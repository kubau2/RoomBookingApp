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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
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
        // Arrange
        List<Reservation> reservations = Collections.singletonList(reservation);
        when(reservationRepository.findAll()).thenReturn(reservations);

        // Act
        List<Reservation> result = reservationService.getReservations();

        // Assert
        assertEquals(1, result.size());
        assertEquals(reservation, result.getFirst());
    }

    @Test
    void createReservation_Success() {
        // Arrange
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(emp));
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        // Act
        Reservation result = reservationService.createReservation(reservation);

        // Assert
        assertNotNull(result);
        assertEquals(reservation, result);
    }

    @Test
    void createReservation_UserNotFoundException() {
        // Arrange
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> reservationService.createReservation(reservation));
    }

    @Test
    void createReservation_RoomNotFoundException() {
        // Arrange
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(emp));
        when(roomRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RoomNotFoundException.class, () -> reservationService.createReservation(reservation));
    }

    @Test
    void createReservation_RoomNotAvailableException() {
        // Arrange
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(emp));
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(reservationRepository.findAllReservationsForTheRoomInGivenTimestampWindow(anyLong(), any(), any())).thenReturn(Collections.singletonList(reservation));

        // Act & Assert
        assertThrows(RoomNotAvailableException.class, () -> reservationService.createReservation(reservation));
    }

    @Test
    void createReservation_ReservationTimestampException() {
        // Arrange
        Reservation invalidReservation = new Reservation(1L, room, Timestamp.valueOf(LocalDateTime.now().plusDays(2)), Timestamp.valueOf(LocalDateTime.now().plusDays(1)), emp);

        // Act & Assert
        assertThrows(ReservationTimestampException.class, () -> reservationService.createReservation(invalidReservation));
    }

    @Test
    void createReservation_InvalidUserData() {
        // Arrange
        reservation.setEmployee(null);

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> reservationService.createReservation(reservation));
    }

    @Test
    void createReservation_InvalidRoomData() {
        // Arrange
        reservation.setRoom(null);

        // Act & Assert
        assertThrows(RoomNotFoundException.class, () -> reservationService.createReservation(reservation));
    }

    @Test
    void deleteReservation() {
        // Arrange
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        JSONObject json = new JSONObject();
        json.put("reservationId", "1");
        json.put("employeeId", "1");

        // Act
        boolean result = reservationService.deleteReservationByIdAndEmployeeId(json);

        // Assert
        assertTrue(result);
    }

    @Test
    void deleteReservation_ReservationNotFoundException() {
        // Arrange
        when(reservationRepository.findById(1L)).thenReturn(Optional.empty());

        JSONObject json = new JSONObject();
        json.put("reservationId", "1");
        json.put("employeeId", "1");

        // Act & Assert
        assertThrows(ReservationNotFoundException.class, () -> reservationService.deleteReservationByIdAndEmployeeId(json));
    }

    @Test
    void deleteReservation_PermissionException() {
        // Arrange
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        JSONObject json = new JSONObject();
        json.put("reservationId", "1");
        json.put("employeeId", "2"); // different employee ID

        // Act & Assert
        assertThrows(PermissionException.class, () -> reservationService.deleteReservationByIdAndEmployeeId(json));
    }
}
