package com.example.hotelversion2.Business.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.hotelversion2.DAO.Models.Room;
import com.example.hotelversion2.DAO.Models.RoomType;
@Service("roomServices") 
public interface RoomServices {
    // Read opérations
    List<Room> getAllRooms();
    Room getRoomById(Long id);
    List<Room> getRoomByView(String view);
List<Room> getRoombyRoomType(RoomType roomType);
List<Room>getRoomByCapacity(int capacity);
List<Room> getRoomByPricePerNightLessThan(double pricePerNight );
List<Room> getRoomSortedByPrice(String order);
    // Create
    Room addRoom(Room room);

    // Update
    Room updateRoom(Room room);

    // Delete
    void deleteRoomById(Long id);
   
}
