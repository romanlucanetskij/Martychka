package com.example.courseworkLuchnetskyi.web;

import com.example.courseworkLuchnetskyi.dto.BookingRequest;
import com.example.courseworkLuchnetskyi.model.Booking;
import com.example.courseworkLuchnetskyi.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> createBooking(@Valid @RequestBody BookingRequest request) {
        return ResponseEntity.ok(bookingService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingService.findAll());
    }

    @GetMapping("/guest/{guestId}")
    public ResponseEntity<List<Booking>> getGuestBookings(@PathVariable Long guestId) {
        return ResponseEntity.ok(bookingService.findByGuest(guestId));
    }

    @GetMapping("/guest/{guestId}/history")
    public ResponseEntity<List<Booking>> getGuestHistory(@PathVariable Long guestId) {
        return ResponseEntity.ok(bookingService.getGuestHistory(guestId));
    }

    @PostMapping("/{bookingId}/cancel")
    public ResponseEntity<Booking> cancelBooking(@PathVariable Long bookingId) {
        return ResponseEntity.ok(bookingService.cancel(bookingId));
    }

    @GetMapping("/{bookingId}/duration")
    public ResponseEntity<Map<String, Object>> getBookingDuration(@PathVariable Long bookingId) {
        return ResponseEntity.ok(bookingService.getDuration(bookingId));
    }

    @GetMapping("/{bookingId}/total-cost")
    public ResponseEntity<Map<String, Object>> getTotalCost(@PathVariable Long bookingId) {
        return ResponseEntity.ok(bookingService.getTotalCost(bookingId));
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long bookingId) {
        bookingService.delete(bookingId);
        return ResponseEntity.noContent().build();
    }
}
