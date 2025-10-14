package com.example.courseworkLuchnetskyi.web;

import com.example.courseworkLuchnetskyi.dto.PaymentRequest;
import com.example.courseworkLuchnetskyi.model.Payment;
import com.example.courseworkLuchnetskyi.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Payment> createPayment(@Valid @RequestBody PaymentRequest request) {
        return ResponseEntity.ok(paymentService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentService.findAll());
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<Payment>> getPaymentsByBooking(@PathVariable Long bookingId) {
        return ResponseEntity.ok(paymentService.findByBooking(bookingId));
    }

    @GetMapping("/total")
    public ResponseEntity<BigDecimal> getTotalPayments() {
        return ResponseEntity.ok(paymentService.getTotalAmount());
    }
}
