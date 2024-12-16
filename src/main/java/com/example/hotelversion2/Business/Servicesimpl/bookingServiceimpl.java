package com.example.hotelversion2.Business.Servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
/*public List<Booking> findBookingsByClientName(String clientName) {
    return bookingRepository.findByCustomer_NameIgnoreCase(clientName);
}/*/

@Override
public List<Booking> getBookingsortedBycheckInDate(String order) {
     Sort.Direction direction= Sort.Direction.ASC;
      if("desc".equalsIgnoreCase(order)){

        direction= Sort.Direction.DESC;
   
}
return bookingRepository.findAll(Sort.by(direction,"checkInDate"));
}

@Override
public Page<Booking> getAllBookingPagination(Pageable pageable) {
    return bookingRepository.findAll(pageable);
}

@Override
public Page<Booking> rechercherbycheckInDate(LocalDate Date, Pageable pageable) {
  return bookingRepository.findByCheckInDate(Date,pageable);
  
  
}


@Override
public Page<Booking> getBookingSortedBycheckInDatePagination(String order, Pageable pageable) {
    if(pageable ==null){
        return null;
    }  
    Sort.Direction direction= Sort.Direction.ASC;
    if("desc".equalsIgnoreCase(order)){
        direction= Sort.Direction.DESC;
    }

    Pageable sortedPageable=PageRequest.of(
      pageable.getPageNumber(),
      pageable.getPageSize(),
      Sort.by(direction,"checkInDate")
  );
  return (bookingRepository.findAll(sortedPageable));
}



}
