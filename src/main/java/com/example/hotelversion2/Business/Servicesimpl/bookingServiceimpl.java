package com.example.hotelversion2.Business.Servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import com.example.hotelversion2.Business.Services.BookingService;
import com.example.hotelversion2.DAO.Repository.BookingRepository;
import com.example.hotelversion2.DAO.entites.Booking;
@Service
public class bookingServiceimpl implements BookingService {
    @Autowired
    BookingRepository bookingRepository;

    @Override
    public Booking getBookingById(Long id) {
        if (id==null)
        return null;
        
        return bookingRepository.findById(id).get();
    }

    @Override
    public List<Booking> getAllBookings() {
        return  bookingRepository.findAll();  }
        
    public Booking update(Booking booking)
    {
        return (bookingRepository.save(booking));
    }
public void remove(Long id)
{
    Booking b =bookingRepository.findById( id).get();
    bookingRepository.delete(b);

}

@Override
public Booking add(Booking booking) {
   return bookingRepository.save(booking);
}

}
