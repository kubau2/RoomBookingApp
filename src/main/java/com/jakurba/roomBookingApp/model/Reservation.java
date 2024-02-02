package com.jakurba.roomBookingApp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "roomId")
    private String roomId;

    @Column(name = "reservationStart")
    private Timestamp reservationStart;

    @Column(name = "reservationEnd")
    private Timestamp reservationEnd;

    @Column(name = "reservationUserId")
    private Timestamp reservationUserId;
}
