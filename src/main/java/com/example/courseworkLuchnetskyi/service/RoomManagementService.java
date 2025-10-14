package com.example.courseworkLuchnetskyi.service;

import com.example.courseworkLuchnetskyi.dto.RoomRequest;
import com.example.courseworkLuchnetskyi.model.Room;
import com.example.courseworkLuchnetskyi.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomManagementService {

    private final RoomRepository roomRepository;

    public Room create(RoomRequest request) {
        roomRepository.findByNumber(request.number()).ifPresent(room -> {
            throw new IllegalArgumentException("Room with number already exists");
        });
        Room room = Room.builder()
                .number(request.number())
                .type(request.type())
                .capacity(request.capacity())
                .pricePerNight(request.pricePerNight())
                .build();
        return roomRepository.save(room);
    }

    @Transactional(readOnly = true)
    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    public Room update(Long id, RoomRequest request) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));
        room.setNumber(request.number());
        room.setType(request.type());
        room.setCapacity(request.capacity());
        room.setPricePerNight(request.pricePerNight());
        return roomRepository.save(room);
    }

    public void delete(Long id) {
        roomRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Room> findAvailable(LocalDate checkIn, LocalDate checkOut) {
        if (checkOut.isBefore(checkIn)) {
            throw new IllegalArgumentException("Check-out date must be after check-in date");
        }
        return roomRepository.findAvailable(checkIn, checkOut);
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getOccupancyStats() {
        List<Map<String, Object>> stats = new ArrayList<>();
        roomRepository.findAll().forEach(room -> {
            long totalBookings = room.getBookings().size();
            long active = room.getBookings().stream()
                    .filter(booking -> !"CANCELLED".equalsIgnoreCase(booking.getStatus()))
                    .count();
            Map<String, Object> entry = new HashMap<>();
            entry.put("roomId", room.getId());
            entry.put("roomNumber", room.getNumber());
            entry.put("totalBookings", totalBookings);
            entry.put("activeBookings", active);
            stats.add(entry);
        });
        return stats;
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getPopularRoomTypes() {
        List<Map<String, Object>> result = new ArrayList<>();
        roomRepository.findPopularRoomTypes().forEach(row -> {
            Map<String, Object> entry = new HashMap<>();
            entry.put("type", row[0]);
            entry.put("bookings", row[1] instanceof Number number ? number.longValue() : row[1]);
            result.add(entry);
        });
        return result;
    }

    @Transactional(readOnly = true)
    public Room getById(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));
    }
}
