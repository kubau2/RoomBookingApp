package com.jakurba.roomBookingApp.service;

import com.jakurba.roomBookingApp.exceptions.PermissionException;
import com.jakurba.roomBookingApp.exceptions.ReservationNotFoundException;
import com.jakurba.roomBookingApp.exceptions.ReservationTimestampException;
import com.jakurba.roomBookingApp.exceptions.RoomNotAvailableException;
import com.jakurba.roomBookingApp.exceptions.RoomNotFoundException;
import com.jakurba.roomBookingApp.exceptions.UserNotFoundException;
import com.jakurba.roomBookingApp.model.Reservation;
import com.jakurba.roomBookingApp.model.Room;
import com.jakurba.roomBookingApp.repository.EmployeeRepository;
import com.jakurba.roomBookingApp.repository.ReservationRepository;
import com.jakurba.roomBookingApp.repository.RoomRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import net.minidev.json.JSONObject;

import java.util.List;

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

    public Reservation createReservation(Reservation reservation) {
        validateInputData(reservation);
        //Find employee based on it's ID and assign it to the reservation
        reservation.setEmployee(employeeRepository.findById(reservation.getEmployee().getId()).orElseThrow(UserNotFoundException::new));

        //Find room based on it's ID and assign it to the reservation
        Room room = roomRepository.findById(reservation.getRoom().getId()).orElseThrow(RoomNotFoundException::new);
        checkRoomsAvailability(reservation);
        reservation.setRoom(room);

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

    private void validateInputData(Reservation reservation) {
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

    private void checkRoomsAvailability(Reservation reservation) {
        Room room = roomRepository.findById(reservation.getRoom().getId()).orElseThrow(RoomNotFoundException::new);
        List<Reservation> reservationsForTheRoom = reservationRepository.findAllReservationsForTheRoomInGivenTimestampWindow(room.getId(), reservation.getReservationStart(), reservation.getReservationEnd()); //hmm, moze tutaj zrobic juz filtr po dacie?
        if (!reservationsForTheRoom.isEmpty()) {
            throw new RoomNotAvailableException();
        }

    }

}
