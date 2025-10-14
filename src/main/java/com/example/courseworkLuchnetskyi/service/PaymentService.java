package com.example.courseworkLuchnetskyi.service;

import com.example.courseworkLuchnetskyi.dto.PaymentRequest;
import com.example.courseworkLuchnetskyi.model.Booking;
import com.example.courseworkLuchnetskyi.model.Payment;
import com.example.courseworkLuchnetskyi.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingService bookingService;

    public Payment create(PaymentRequest request) {
        Booking booking = bookingService.getById(request.bookingId());
        Payment payment = Payment.builder()
                .booking(booking)
                .amount(request.amount())
                .paymentDate(request.paymentDate())
                .build();
        return paymentRepository.save(payment);
    }

    @Transactional(readOnly = true)
    public List<Payment> findByBooking(Long bookingId) {
        return paymentRepository.findByBookingId(bookingId);
    }

    @Transactional(readOnly = true)
    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public BigDecimal getTotalAmount() {
        return paymentRepository.sumAllPayments();
    }
}
