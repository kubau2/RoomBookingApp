package com.jakurba.roomBookingApp.controller;

import com.jakurba.roomBookingApp.model.Room;
import com.jakurba.roomBookingApp.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RoomController {

    @Autowired
    RoomService roomservice;

    @GetMapping(value = "/rooms")
    public List<Room> readRoom() {
        return roomservice.getRooms();
    }
    
}