package com.example.courseworkLuchnetskyi.repository;

import com.example.courseworkLuchnetskyi.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    Optional<Room> findByNumber(String number);

    @Query("SELECT r FROM Room r WHERE r.id NOT IN (" +
            "SELECT b.room.id FROM Booking b WHERE " +
            "(b.checkInDate <= :checkOut AND b.checkOutDate >= :checkIn) AND b.status <> 'CANCELLED')")
    List<Room> findAvailable(@Param("checkIn") LocalDate checkIn, @Param("checkOut") LocalDate checkOut);

    @Query("SELECT r.type AS type, COUNT(b.id) AS count FROM Room r " +
            "LEFT JOIN r.bookings b WITH b.status <> 'CANCELLED' " +
            "GROUP BY r.type ORDER BY COUNT(b.id) DESC")
    List<Object[]> findPopularRoomTypes();
}
