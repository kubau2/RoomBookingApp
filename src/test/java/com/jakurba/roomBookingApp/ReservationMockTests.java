package com.jakurba.roomBookingApp;

import com.jakurba.roomBookingApp.exceptions.*;
import com.jakurba.roomBookingApp.model.Employee;
import com.jakurba.roomBookingApp.model.Reservation;
import com.jakurba.roomBookingApp.model.Room;
import com.jakurba.roomBookingApp.repository.EmployeeRepository;
import com.jakurba.roomBookingApp.repository.ReservationRepository;
import com.jakurba.roomBookingApp.repository.RoomRepository;
import com.jakurba.roomBookingApp.service.ReservationService;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

class ReservationMockTests {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;

    private Room room;
    private Employee emp;
    private Reservation reservation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Common setup for tests
        room = new Room(1L, "Colorful", "5_5", 2L, 15L, false, true);
        emp = new Employee(1L, "johny@email.com", "Johny", "Blaze");
        reservation = new Reservation(1L, room, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now().plusHours(1)), emp);
    }

    @Test
    void getReservations() {
        // Given
        List<Reservation> reservations = Collections.singletonList(reservation);
        when(reservationRepository.findAll()).thenReturn(reservations);

        // When
        List<Reservation> result = reservationService.getReservations();

        // Then
        assertEquals(1, result.size());
        assertEquals(reservation, result.getFirst());
    }

    @Test
    void createReservation_Success() {
        // Given
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(emp));
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        // When
        Reservation result = reservationService.createReservation(reservation);

        // Then
        assertNotNull(result);
        assertEquals(reservation, result);
    }

    @Test
    void createReservation_UserNotFoundException() {
        // Given
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(UserNotFoundException.class, () -> reservationService.createReservation(reservation));
    }

    @Test
    void createReservation_RoomNotFoundException() {
        // Given
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(emp));
        when(roomRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RoomNotFoundException.class, () -> reservationService.createReservation(reservation));
    }

    @Test
    void createReservation_RoomNotAvailableException() {
        // Given
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(emp));
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(reservationRepository.findAllReservationsForTheRoomInGivenTimestampWindow(anyLong(), any(), any())).thenReturn(Collections.singletonList(reservation));

        // When & Then
        assertThrows(RoomNotAvailableException.class, () -> reservationService.createReservation(reservation));
    }

    @Test
    void createReservation_ReservationTimestampException() {
        // Given
        Reservation invalidReservation = new Reservation(1L, room, Timestamp.valueOf(LocalDateTime.now().plusDays(2)), Timestamp.valueOf(LocalDateTime.now().plusDays(1)), emp);

        // When & Then
        assertThrows(ReservationTimestampException.class, () -> reservationService.createReservation(invalidReservation));
    }

    @Test
    void createReservation_InvalidUserData() {
        // Given
        reservation.setEmployee(null);

        // When & Then
        assertThrows(UserNotFoundException.class, () -> reservationService.createReservation(reservation));
    }

    @Test
    void createReservation_InvalidRoomData() {
        // Given
        reservation.setRoom(null);

        // When & Then
        assertThrows(RoomNotFoundException.class, () -> reservationService.createReservation(reservation));
    }

    @Test
    void deleteReservation() {
        // Given
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        JSONObject json = new JSONObject();
        json.put("reservationId", "1");
        json.put("employeeId", "1");

        // When
        boolean result = reservationService.deleteReservationByIdAndEmployeeId(json);

        // Then
        assertTrue(result);
    }

    @Test
    void deleteReservation_ReservationNotFoundException() {
        // Given
        when(reservationRepository.findById(1L)).thenReturn(Optional.empty());

        JSONObject json = new JSONObject();
        json.put("reservationId", "1");
        json.put("employeeId", "1");

        // When & Then
        assertThrows(ReservationNotFoundException.class, () -> reservationService.deleteReservationByIdAndEmployeeId(json));
    }

    @Test
    void deleteReservation_PermissionException() {
        // Given
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        JSONObject json = new JSONObject();
        json.put("reservationId", "1");
        json.put("employeeId", "2"); // different employee ID

        // When & Then
        assertThrows(PermissionException.class, () -> reservationService.deleteReservationByIdAndEmployeeId(json));
    }

}
