package com.example.hotelversion2.Business.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.hotelversion2.DAO.Models.Room;
@Service("roomServices") 
public interface RoomServices {
    // Read opérations
    List<Room> getAllRooms();
    Room getRoomById(Long id); // throws Exception (si nécessaire)
    

    // Create
    Room addRoom(Room room);

    // Update
    Room updateRoom(Room room);

    // Delete
    void deleteRoomById(Long id);
}
