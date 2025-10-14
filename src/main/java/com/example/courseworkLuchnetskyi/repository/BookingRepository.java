package com.example.courseworkLuchnetskyi.repository;

import com.example.courseworkLuchnetskyi.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByGuestId(Long guestId);

    @Query("SELECT b FROM Booking b WHERE b.room.id = :roomId AND b.status <> 'CANCELLED' " +
            "AND ((b.checkInDate <= :checkOut AND b.checkOutDate >= :checkIn))")
    List<Booking> findRoomReservations(@Param("roomId") Long roomId,
                                       @Param("checkIn") LocalDate checkIn,
                                       @Param("checkOut") LocalDate checkOut);
}
