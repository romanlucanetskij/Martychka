package com.example.courseworkLuchnetskyi.web;

import com.example.courseworkLuchnetskyi.dto.RoomRequest;
import com.example.courseworkLuchnetskyi.model.Room;
import com.example.courseworkLuchnetskyi.service.RoomManagementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomManagementService roomService;

    @PostMapping
    public ResponseEntity<Room> createRoom(@Valid @RequestBody RoomRequest request) {
        return ResponseEntity.ok(roomService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<Room>> getRooms() {
        return ResponseEntity.ok(roomService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id, @Valid @RequestBody RoomRequest request) {
        return ResponseEntity.ok(roomService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/available")
    public ResponseEntity<List<Room>> getAvailableRooms(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate
    ) {
        return ResponseEntity.ok(roomService.findAvailable(checkInDate, checkOutDate));
    }

    @GetMapping("/occupancy")
    public ResponseEntity<List<Map<String, Object>>> getOccupancyStats() {
        return ResponseEntity.ok(roomService.getOccupancyStats());
    }

    @GetMapping("/popular-types")
    public ResponseEntity<List<Map<String, Object>>> getPopularRoomTypes() {
        return ResponseEntity.ok(roomService.getPopularRoomTypes());
    }
}
