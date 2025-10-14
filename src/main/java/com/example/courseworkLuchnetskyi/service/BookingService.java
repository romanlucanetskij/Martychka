package com.example.courseworkLuchnetskyi.service;

import com.example.courseworkLuchnetskyi.dto.BookingRequest;
import com.example.courseworkLuchnetskyi.model.Booking;
import com.example.courseworkLuchnetskyi.model.Guest;
import com.example.courseworkLuchnetskyi.model.Room;
import com.example.courseworkLuchnetskyi.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingService {

    private final BookingRepository bookingRepository;
    private final GuestService guestService;
    private final RoomManagementService roomService;

    public Booking create(BookingRequest request) {
        if (!request.checkOutDate().isAfter(request.checkInDate())) {
            throw new IllegalArgumentException("Check-out date must be after check-in date");
        }
        Guest guest = guestService.getById(request.guestId());
        Room room = roomService.getById(request.roomId());

        ensureAvailability(room.getId(), request.checkInDate(), request.checkOutDate());

        Booking booking = Booking.builder()
                .guest(guest)
                .room(room)
                .checkInDate(request.checkInDate())
                .checkOutDate(request.checkOutDate())
                .status("CONFIRMED")
                .build();
        return bookingRepository.save(booking);
    }

    private void ensureAvailability(Long roomId, LocalDate checkIn, LocalDate checkOut) {
        boolean conflict = bookingRepository.findRoomReservations(roomId, checkIn, checkOut)
                .stream()
                .anyMatch(existing -> existing.getStatus() != null && !"CANCELLED".equalsIgnoreCase(existing.getStatus()));
        if (conflict) {
            throw new IllegalArgumentException("Room is not available for the selected dates");
        }
    }

    @Transactional(readOnly = true)
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Booking> findByGuest(Long guestId) {
        return bookingRepository.findByGuestId(guestId);
    }

    public Booking cancel(Long bookingId) {
        Booking booking = getById(bookingId);
        booking.setStatus("CANCELLED");
        return bookingRepository.save(booking);
    }

    public void delete(Long bookingId) {
        bookingRepository.deleteById(bookingId);
    }

    @Transactional(readOnly = true)
    public Booking getById(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getDuration(Long bookingId) {
        Booking booking = getById(bookingId);
        long nights = ChronoUnit.DAYS.between(booking.getCheckInDate(), booking.getCheckOutDate());
        Map<String, Object> response = new HashMap<>();
        response.put("bookingId", bookingId);
        response.put("nights", nights);
        return response;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getTotalCost(Long bookingId) {
        Booking booking = getById(bookingId);
        long nights = Math.max(1, ChronoUnit.DAYS.between(booking.getCheckInDate(), booking.getCheckOutDate()));
        BigDecimal stayCost = booking.getRoom().getPricePerNight()
                .multiply(BigDecimal.valueOf(nights));
        BigDecimal servicesCost = booking.getRoom().getServices().stream()
                .map(service -> service.getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal total = stayCost.add(servicesCost);
        Map<String, Object> response = new HashMap<>();
        response.put("bookingId", bookingId);
        response.put("totalCost", total);
        response.put("stayCost", stayCost);
        response.put("servicesCost", servicesCost);
        return response;
    }

    @Transactional(readOnly = true)
    public List<Booking> getGuestHistory(Long guestId) {
        LocalDate today = LocalDate.now();
        return bookingRepository.findByGuestId(guestId).stream()
                .filter(booking -> booking.getCheckOutDate().isBefore(today)
                        || "CANCELLED".equalsIgnoreCase(booking.getStatus()))
                .toList();
    }
}
