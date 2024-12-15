package com.example.hotelversion2.DAO.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hotelversion2.DAO.entites.Customer;
import java.util.List;



public interface CustommerRepository extends JpaRepository <Customer, Long> {
    Page<Customer> findByEmail(String email, Pageable pageable);
}
