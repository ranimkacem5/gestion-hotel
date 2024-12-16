package com.example.hotelversion2.Business.Services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import com.example.hotelversion2.DAO.entites.Booking;
import com.example.hotelversion2.DAO.entites.Customer;

@Service
public interface BookingService {
  Booking add(Booking booking);
  Booking getBookingById(Long id );
List<Booking> getAllBookings();
Booking update(Booking booking);
void remove(Long id);
List<Booking>getBookingsortedBycheckInDate(String order);
Page<Booking>getAllBookingPagination(Pageable pageable);
Page<Booking>rechercherbycheckInDate(LocalDate Date  ,Pageable pageable);
Page<Booking> getBookingSortedBycheckInDatePagination(String order,Pageable pageable);

//List<Booking> findBookingsByClientName(String clientName);

    
}
