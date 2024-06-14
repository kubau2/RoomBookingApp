package com.jakurba.roomBookingApp.controller;

import com.jakurba.roomBookingApp.exceptions.ReservationTimestampException;
import com.jakurba.roomBookingApp.exceptions.RoomNotAvailableException;
import com.jakurba.roomBookingApp.exceptions.RoomNotFoundException;
import com.jakurba.roomBookingApp.exceptions.UserNotFoundException;
import com.jakurba.roomBookingApp.model.Reservation;
import com.jakurba.roomBookingApp.service.ReservationService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @PostMapping(value = "/reservation/new")
    public ResponseEntity<Object> createReservation(@RequestBody Reservation reservation) {
        try {
            return ResponseEntity.ok(reservationService.createReservation(reservation));
        } catch (RoomNotFoundException e) {
            return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
        } catch(UserNotFoundException e){
            return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
        } catch(ReservationTimestampException e){
            return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
        }catch(RoomNotAvailableException e){
            return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
        }
    }

    @GetMapping(value = "/reservations")
    public List<Reservation> readReservations() {
        return reservationService.getReservations();
    }

    @PutMapping(value = "/reservation/update")
    public Reservation readReservations(@RequestBody Reservation reservation) {
        //TODO
        return null;
//        return ReservationService.updateClient(client);
    }

    @DeleteMapping(value = "/reservation")
    public boolean deleteReservation(@RequestBody JSONObject json) {
        return reservationService.deleteReservationByIdAndEmployeeId(json);
    }
}
