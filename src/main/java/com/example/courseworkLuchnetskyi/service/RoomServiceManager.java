package com.example.courseworkLuchnetskyi.service;

import com.example.courseworkLuchnetskyi.dto.RoomServiceRequest;
import com.example.courseworkLuchnetskyi.model.Room;
import com.example.courseworkLuchnetskyi.model.RoomService;
import com.example.courseworkLuchnetskyi.repository.RoomServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomServiceManager {

    private final RoomServiceRepository roomServiceRepository;
    private final RoomManagementService roomManagementService;

    public RoomService create(RoomServiceRequest request) {
        Room room = roomManagementService.getById(request.roomId());
        RoomService roomService = RoomService.builder()
                .room(room)
                .description(request.description())
                .price(request.price())
                .build();
        return roomServiceRepository.save(roomService);
    }

    @Transactional(readOnly = true)
    public List<RoomService> findByRoom(Long roomId) {
        return roomServiceRepository.findByRoomId(roomId);
    }
}
