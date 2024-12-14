package com.example.hotelversion2.Business.Services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.hotelversion2.DAO.entites.Room;
import com.example.hotelversion2.Web.Models.RoomType;
@Service
public interface RoomServices {
    // Read opérations
    List<Room> getAllRooms();
    Room getRoomById(Long id);
    List<Room> getRoomByView(String view);
List<Room> getRoombyRoomType(RoomType roomType);
List<Room>getRoomByCapacity(int capacity);
List<Room> getRoomByPricePerNightLessThan(double pricePerNight );
List<Room> getRoomSortedByPrice(String order);
Page<Room> getRoomSortedByPricePagination(String order,Pageable pageable);
Page<Room>getAllRoomPagination(Pageable pageable);
    // Create
    Room addRoom(Room room);

    // Update
    Room updateRoom(Room room);

    // Delete
    void deleteRoom(Room room);
   
}
