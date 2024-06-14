package com.jakurba.roomBookingApp.service;

import com.jakurba.roomBookingApp.exceptions.PermissionException;
import com.jakurba.roomBookingApp.exceptions.ReservationNotFoundException;
import com.jakurba.roomBookingApp.exceptions.ReservationTimestampException;
import com.jakurba.roomBookingApp.exceptions.RoomNotAvailableException;
import com.jakurba.roomBookingApp.exceptions.RoomNotFoundException;
import com.jakurba.roomBookingApp.exceptions.UserNotFoundException;
import com.jakurba.roomBookingApp.model.Employee;
import com.jakurba.roomBookingApp.model.Reservation;
import com.jakurba.roomBookingApp.model.Room;
import com.jakurba.roomBookingApp.repository.EmployeeRepository;
import com.jakurba.roomBookingApp.repository.ReservationRepository;
import com.jakurba.roomBookingApp.repository.RoomRepository;
import jakarta.transaction.Transactional;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.minidev.json.JSONObject;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    RoomRepository roomRepository;

    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }

    public Reservation createReservation(Reservation reservation) throws UserNotFoundException, RoomNotFoundException, ReservationTimestampException {
        validateInputData(reservation);
        //Find employee based on it's ID and assign it to the reservation
        Optional<Employee> emp = employeeRepository.findById(reservation.getEmployee().getId());
        if (emp.isPresent()){
            reservation.setEmployee(emp.get());
        } else {
            throw new UserNotFoundException();
        }


        //Find room based on it's ID and assign it to the reservation
        Optional<Room> room = roomRepository.findById(reservation.getRoom().getId());
        if (room.isEmpty()){
            throw new RoomNotFoundException();
        }
        checkRoomsAvailability(reservation);
        reservation.setRoom(room.get());

        return reservationRepository.save(reservation);
    }

    @Transactional
    public boolean deleteReservationByIdAndEmployeeId(JSONObject json) {
        Long reservationId = Long.parseLong(json.getAsString("reservationId"));
        Long employeeId = Long.parseLong(json.getAsString("employeeId"));

        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(ReservationNotFoundException::new);
        if (reservation.getEmployee().getId().equals(employeeId)) {
            reservationRepository.deleteById(reservationId);
            return true;
        } else {
            throw new PermissionException();
        }
    }

    private void validateInputData(Reservation reservation) throws UserNotFoundException, ReservationTimestampException, RoomNotFoundException {
        if (reservation.getEmployee() == null || reservation.getEmployee().getId() == null) {
            throw new UserNotFoundException();
        }

        if (reservation.getReservationEnd() == null || reservation.getReservationStart() == null || reservation.getReservationEnd().before(reservation.getReservationStart())) {
            throw new ReservationTimestampException();
        }

        if (reservation.getRoom() == null || reservation.getRoom().getId() == null) {
            throw new RoomNotFoundException();
        }

    }

    private void checkRoomsAvailability(Reservation reservation) throws RoomNotAvailableException{
        Room room = roomRepository.findById(reservation.getRoom().getId()).orElseThrow(RoomNotFoundException::new);
        List<Reservation> reservationsForTheRoom = reservationRepository.findAllReservationsForTheRoomInGivenTimestampWindow(room.getId(), reservation.getReservationStart(), reservation.getReservationEnd()); //hmm, moze tutaj zrobic juz filtr po dacie?
        if (!reservationsForTheRoom.isEmpty()) {
            throw new RoomNotAvailableException();
        }

    }

}
