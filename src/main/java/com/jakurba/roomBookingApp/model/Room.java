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

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "room_Name")
    private String roomName;

    @Column(name = "room_Number")
    private String roomNumber;

    @Column(name = "floor")
    private Long floor;

    @Column(name = "available_Seats")
    private Long availableSeats;

    @Column(name = "phone_Available")
    private boolean phoneAvailable;

    @Column(name = "tv_Available")
    private boolean tvAvailable;
}
