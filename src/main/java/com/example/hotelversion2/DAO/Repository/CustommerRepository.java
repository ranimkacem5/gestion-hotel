package com.example.hotelversion2.DAO.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hotelversion2.DAO.entites.Customer;


public interface CustommerRepository extends JpaRepository <Customer, Long> {
    
}
