package com.example.courseworkLuchnetskyi.repository;

import com.example.courseworkLuchnetskyi.model.RoomService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomServiceRepository extends JpaRepository<RoomService, Long> {

    List<RoomService> findByRoomId(Long roomId);
}
