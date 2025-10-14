package com.example.courseworkLuchnetskyi.web;

import com.example.courseworkLuchnetskyi.dto.GuestRequest;
import com.example.courseworkLuchnetskyi.model.Guest;
import com.example.courseworkLuchnetskyi.service.GuestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guests")
@RequiredArgsConstructor
public class GuestController {

    private final GuestService guestService;

    @PostMapping
    public ResponseEntity<Guest> createGuest(@Valid @RequestBody GuestRequest request) {
        return ResponseEntity.ok(guestService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<Guest>> getGuests() {
        return ResponseEntity.ok(guestService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Guest> updateGuest(@PathVariable Long id, @Valid @RequestBody GuestRequest request) {
        return ResponseEntity.ok(guestService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGuest(@PathVariable Long id) {
        guestService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
