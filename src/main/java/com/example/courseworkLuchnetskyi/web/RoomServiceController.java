package com.example.courseworkLuchnetskyi.web;

import com.example.courseworkLuchnetskyi.dto.RoomServiceRequest;
import com.example.courseworkLuchnetskyi.model.RoomService;
import com.example.courseworkLuchnetskyi.service.RoomServiceManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/room-services")
@RequiredArgsConstructor
public class RoomServiceController {

    private final RoomServiceManager roomServiceManager;

    @PostMapping
    public ResponseEntity<RoomService> createRoomService(@Valid @RequestBody RoomServiceRequest request) {
        return ResponseEntity.ok(roomServiceManager.create(request));
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<RoomService>> getRoomServices(@PathVariable Long roomId) {
        return ResponseEntity.ok(roomServiceManager.findByRoom(roomId));
    }
}
