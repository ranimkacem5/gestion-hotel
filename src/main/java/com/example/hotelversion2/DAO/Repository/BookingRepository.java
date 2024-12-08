package com.example.hotelversion2.DAO.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hotelversion2.DAO.entites.Booking;


@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    
} 