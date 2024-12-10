package com.example.hotelversion2.Business.Services;

import org.springframework.stereotype.Service;
import java.util.List;
import com.example.hotelversion2.DAO.entites.Booking;

@Service
public interface BookingService {
  Booking add(Booking booking);
  Booking getBookingById(Long id );
List<Booking> getAllBookings();
Booking update(Booking booking);
void remove(Long id);

    
}
