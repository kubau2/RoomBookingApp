package com.jakurba.roomBookingApp.service;

import com.jakurba.roomBookingApp.exceptions.RoomNotFoundException;
import com.jakurba.roomBookingApp.model.Room;
import com.jakurba.roomBookingApp.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    RoomRepository roomRepository;

    public List<Room> getRooms() {
        return roomRepository.findAll();
    }

    public Room findRoomByID(Long id){
        return roomRepository.findById(id).orElseThrow(RoomNotFoundException::new);
    }

}
