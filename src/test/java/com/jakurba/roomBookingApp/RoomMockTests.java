package com.jakurba.roomBookingApp;

import com.jakurba.roomBookingApp.exceptions.RoomNotFoundException;
import com.jakurba.roomBookingApp.model.Room;
import com.jakurba.roomBookingApp.repository.RoomRepository;
import com.jakurba.roomBookingApp.service.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class RoomMockTests {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomService roomService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getRooms() {
        // Given
        Room room1 = new Room(1L, "Violet", "5_4", 3L, 5L, true, false);
        Room room2 = new Room(2L, "Pink", "2_4", 1L, 15L, true, true);
        List<Room> rooms = Arrays.asList(room1, room2);
        when(roomRepository.findAll()).thenReturn(rooms);

        // When
        List<Room> result = roomService.getRooms();

        // Then
        assertEquals(2, result.size());
        assertEquals(room1, result.get(0));
        assertEquals(room2, result.get(1));
        verify(roomRepository, times(1)).findAll();
    }

    @Test
    void findRoomByID() {
        // Given
        Long roomId = 1L;
        Room room = new Room(1L, "Violet", "5_4", 3L, 5L, true, false);
        when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));

        // When
        Room result = roomService.findRoomByID(roomId);

        // Then
        assertEquals(room, result);
        verify(roomRepository, times(1)).findById(roomId);
    }

    @Test
    void findRoomByID_RoomNotFoundException() {
        // Given
        Long roomId = 1L;
        when(roomRepository.findById(roomId)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(RoomNotFoundException.class, () -> {
            roomService.findRoomByID(roomId);
        });
        verify(roomRepository, times(1)).findById(roomId);
    }

}
