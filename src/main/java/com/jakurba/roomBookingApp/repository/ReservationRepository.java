package com.jakurba.roomBookingApp.repository;

import com.jakurba.roomBookingApp.model.Reservation;
import com.jakurba.roomBookingApp.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findById(Long id);

    List<Reservation> findAllByRoom(Room room);

    @Query(value = "SELECT * FROM reservation e WHERE room_id = ?1 AND (e.reservation_Start BETWEEN ?2 AND ?3 OR e.reservation_End BETWEEN ?2 AND ?3)", nativeQuery = true)
    List<Reservation> findAllReservationsForTheRoomInGivenTimestampWindow(Long roomId, Timestamp startTimestamp, Timestamp endTimestamp);

}