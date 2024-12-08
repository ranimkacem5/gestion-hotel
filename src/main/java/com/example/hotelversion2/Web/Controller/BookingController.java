package com.example.hotelversion2.Web.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.hotelversion2.Business.Services.BookingService;
import com.example.hotelversion2.Business.Services.Customerservice;
import com.example.hotelversion2.Business.Services.RoomServices;
import com.example.hotelversion2.DAO.entites.Booking;
import com.example.hotelversion2.Web.Models.BookingForm;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
@RequestMapping("bookings")
public class BookingController {
  
  public final BookingService bookingservice;
  public final Customerservice customerservice;
  public final RoomServices roomServices;
 public BookingController(BookingService bookingservice,Customerservice customerservice, RoomServices roomServices)
  {this.bookingservice=bookingservice;

    this.customerservice=customerservice;
    this.roomServices=roomServices;
  }
  @RequestMapping(path="create", method=RequestMethod.GET)
  public String showaddbookingForm(Model model) {
    model.addAttribute("BookingForm", new BookingForm());
    model.addAttribute("rooms",roomServices.getAllRooms());
    model.addAttribute("customers", customerservice.getallcustommers());
      return "bookings/add-booking";
  }
  
}
