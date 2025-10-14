package com.example.courseworkLuchnetskyi.service;

import com.example.courseworkLuchnetskyi.dto.GuestRequest;
import com.example.courseworkLuchnetskyi.model.Guest;
import com.example.courseworkLuchnetskyi.repository.GuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GuestService {

    private final GuestRepository guestRepository;

    public Guest create(GuestRequest request) {
        guestRepository.findByEmail(request.email()).ifPresent(guest -> {
            throw new IllegalArgumentException("Guest with email already exists");
        });
        Guest guest = Guest.builder()
                .name(request.name())
                .phone(request.phone())
                .email(request.email())
                .build();
        return guestRepository.save(guest);
    }

    @Transactional(readOnly = true)
    public List<Guest> findAll() {
        return guestRepository.findAll();
    }

    public Guest update(Long id, GuestRequest request) {
        Guest guest = guestRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Guest not found"));
        guestRepository.findByEmail(request.email())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("Guest with email already exists");
                });
        guest.setName(request.name());
        guest.setPhone(request.phone());
        guest.setEmail(request.email());
        return guestRepository.save(guest);
    }

    public void delete(Long id) {
        guestRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Guest getById(Long guestId) {
        return guestRepository.findById(guestId)
                .orElseThrow(() -> new IllegalArgumentException("Guest not found"));
    }
}
