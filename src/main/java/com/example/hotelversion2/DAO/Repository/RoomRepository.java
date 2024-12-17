package com.example.hotelversion2.DAO.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hotelversion2.DAO.entites.Room;
import com.example.hotelversion2.Web.Models.RoomType;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByRoomType(RoomType roomType); 
    List<Room> findByPricePerNightLessThan(double pricePerNight);  
    List<Room> findByCapacity(int capacity);  
    List<Room> findByView(String view);
  
    Page<Room> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Room> findByRoomTypeAndViewOrderByPricePerNightAsc(RoomType roomType, String view, Pageable pageable);
    Page<Room> findByRoomTypeAndViewOrderByPricePerNightDesc(RoomType roomType, String view, Pageable pageable);
    Page<Room> findByRoomTypeOrderByPricePerNightAsc(RoomType roomType, Pageable pageable);
    Page<Room> findByRoomTypeOrderByPricePerNightDesc(RoomType roomType, Pageable pageable);
    Page<Room> findByViewOrderByPricePerNightAsc(String view, Pageable pageable);
    Page<Room> findByViewOrderByPricePerNightDesc(String view, Pageable pageable);



   
}
