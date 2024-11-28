package com.example.hotelversion2.DAO.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hotelversion2.DAO.Models.Room;
import com.example.hotelversion2.DAO.Models.RoomType;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByRoomType(RoomType roomType); 
    List<Room> findByPricePerNightLessThan(double pricePerNight);  
    List<Room> findByCapacityGreaterThanEqual(int capacity);  
    List<Room> findByRoomTypeAndPricePerNightLessThan(RoomType roomType, double pricePerNight); 
}
