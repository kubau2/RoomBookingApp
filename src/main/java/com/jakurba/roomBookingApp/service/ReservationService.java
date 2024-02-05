package com.jakurba.roomBookingApp.service;

import com.jakurba.roomBookingApp.model.Reservation;
import com.jakurba.roomBookingApp.model.Room;
import com.jakurba.roomBookingApp.repository.EmployeeRepository;
import com.jakurba.roomBookingApp.repository.ReservationRepository;
import com.jakurba.roomBookingApp.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.EmptyStackException;
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

    public Reservation createReservation(Reservation reservation){
        validateInputData(reservation);
        //TODO exception, eg. ProductNotFoundException(productId)
        //Find employee based on it's ID and assign it to the reservation
        reservation.setEmployee(employeeRepository.findById(reservation.getEmployee().getId()).orElseThrow(() -> new EmptyStackException()));

        //Find room based on it's ID and assign it to the reservation
        Room room = roomRepository.findById(reservation.getRoom().getId()).orElseThrow(() -> new EmptyStackException());
        checkRoomsAvailability(reservation);
        reservation.setRoom(room);

        return reservationRepository.save(reservation);
    }

    private void validateInputData(Reservation reservation){
        if (reservation.getEmployee().getId()==null){
            //TODO exception, eg. ProductNotFoundException(productId)
            throw new EmptyStackException();
        }
        if (reservation.getReservationEnd() != null && reservation.getReservationStart() != null){
          //TODO: pomysl nad parsowaniem. Bo inaczej rzuca bad request (400)
            if (reservation.getReservationEnd().before(reservation.getReservationStart())){
                //TODO: Throw invalid timeStampError
                throw new EmptyStackException();
            }
        } else{
            //TODO exception, eg. ProductNotFoundException(productId)
            throw new EmptyStackException();
        }

        if (reservation.getRoom().getId()==null){
            //TODO exception, eg. ProductNotFoundException(productId)
            throw new EmptyStackException();
        }

    }

    private void checkRoomsAvailability(Reservation reservation){
        Room room = roomRepository.findById(reservation.getRoom().getId()).orElseThrow(() -> new EmptyStackException());
        List<Reservation> reservationsForTheRoom = reservationRepository.findAllReservationsForTheRoomInGivenTimestampWindow(room.getId(), reservation.getReservationStart(), reservation.getReservationEnd()); //hmm, moze tutaj zrobic juz filtr po dacie?
        if (!reservationsForTheRoom.isEmpty()){
//TODO: Throw new RoomNotAvaiableException
            throw new EmptyStackException();
        }

    }

}
