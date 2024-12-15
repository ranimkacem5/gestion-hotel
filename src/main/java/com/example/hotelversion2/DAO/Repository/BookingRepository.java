package com.example.hotelversion2.DAO.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hotelversion2.DAO.entites.Booking;
import com.example.hotelversion2.DAO.entites.Customer;


@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    //age<Booking> findByEmail(String email, Pageable pageable);
} 