package com.jakurba.roomBookingApp.controller;

import com.jakurba.roomBookingApp.model.Room;
import com.jakurba.roomBookingApp.service.RoomService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RoomController {

    RoomService roomservice;

    public RoomController(RoomService roomservice) {
        this.roomservice = roomservice;
    }

    @GetMapping(value = "/rooms")
    public List<Room> readRoom() {
        return roomservice.getRooms();
    }

    @GetMapping(value = "/rooms/findById")
    public Room findEmployeeById(@RequestBody Room room) {
        return roomservice.findRoomByID(room.getId());
    }
    
}
