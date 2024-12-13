package com.example.hotelversion2.Web.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.hotelversion2.Business.Services.BookingService;
import com.example.hotelversion2.Business.Services.Customerservice;
import com.example.hotelversion2.Business.Services.RoomServices;
import com.example.hotelversion2.DAO.entites.Booking;
import com.example.hotelversion2.DAO.entites.Customer;
import com.example.hotelversion2.DAO.entites.Room;
import com.example.hotelversion2.Web.Models.BookingForm;
import com.example.hotelversion2.Web.Models.Bookingstatus;
import com.example.hotelversion2.Web.Models.Paymentstatus;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
@RequestMapping("bookings")
public class BookingController {
  
  public final BookingService bookingservice;
  public final Customerservice customerservice;
  public final RoomServices roomServices;
  private static final Logger logger = LoggerFactory.getLogger(RoomController.class);
 public BookingController(BookingService bookingservice,Customerservice customerservice, RoomServices roomServices)
  {this.bookingservice=bookingservice;

    this.customerservice=customerservice;
    this.roomServices=roomServices;
  }
  @RequestMapping(path="", method=RequestMethod.GET)
  public String ListBooking(Model model) {
    model.addAttribute("bookings",bookingservice.getAllBookings() );
      return "bookings/list-booking";
  }
  
  @RequestMapping(path="create", method=RequestMethod.GET)
  public String showaddbookingForm(Model model) {
    model.addAttribute("BookingForm", new BookingForm());
    model.addAttribute("rooms",roomServices.getAllRooms());
    model.addAttribute("payment_status1",   Paymentstatus.values());
     model.addAttribute("booking_status1", Bookingstatus.values());
    model.addAttribute("customers", customerservice.getallcustommers());
      return "bookings/add-booking";
  }

  @RequestMapping(path="create", method=RequestMethod.POST)
  public String create (@Valid @ModelAttribute BookingForm bookingForm,BindingResult br,Model model) {
    if(br.hasErrors())
    {return "bookings/list-booking";}
    Room r= roomServices.getRoomById(bookingForm.getRoomId());
    Customer c=customerservice.getCustomerbyId(bookingForm.getCustomerId());
    Booking b =new Booking(null,r,c,bookingForm.getCheck_in_date(),bookingForm.getCheck_out_date(),bookingForm.getPayment_status(),bookingForm.getBooking_status(),bookingForm.getNombrePersonnes(),bookingForm.getTotal_amount());
    bookingservice.add(b);
      return "redirect:/bookings";
  }
  @RequestMapping(path="/{id}/delete", method=RequestMethod.POST)
  public String delete(@PathVariable Long id ,Model model ) {
    
    bookingservice.remove(id);
      return "redirect:/bookings";
  }
  
  @RequestMapping(path="/{id}/edit", method=RequestMethod.GET)
  public String showeditform(@PathVariable Long id , Model model ) {
    
   Booking b= bookingservice.getBookingById(id);
   BookingForm bookingform =new BookingForm(b.getCustomer().getId(),b.getRoom().getRoomId(),b.getCheckInDate(),b.getCheckOutDate(),b.getPaymentStatus(),b.getBookingStatus(),b.getNombrePersonnes(),b.getTotalAmount());
   bookingform.setCheck_in_date(b.getCheckInDate());
   logger.error(b.getCheckInDate().toString());
   bookingform.setCheck_out_date(b.getCheckOutDate());
   System.out.println("Check-in date: " + bookingform.getCheck_in_date());
System.out.println("Check-out date: " + bookingform.getCheck_out_date());
   model.addAttribute("BookingForm", bookingform);  
   model.addAttribute("id", id);
   model.addAttribute("rooms", roomServices.getAllRooms());
   model.addAttribute("customers", customerservice.getallcustommers());
   model.addAttribute("payment_status1", Paymentstatus.values());
   model.addAttribute("booking_status1", Bookingstatus.values());
   return "bookings/edit-booking";
  }
  @RequestMapping(path="/{id}/edit", method=RequestMethod.POST)
 public String edit(@PathVariable Long id ,@Valid @ModelAttribute BookingForm bookingform, BindingResult br,Model model) {
    /*   if(br.hasErrors())
    {
      br.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
      model.addAttribute("error","invalide input");
      return"bookings/edit-booking";
    }*/
    Booking b=bookingservice.getBookingById(id);
Room r=roomServices.getRoomById(bookingform.getRoomId()); 
Customer c =customerservice.getCustomerbyId(bookingform.getCustomerId());
b.setBookingStatus(bookingform.getBooking_status());
System.out.println(b.getCheckInDate());
System.out.println(b.getCheckOutDate());
b.setPaymentStatus(bookingform.getPayment_status());
b.setNombrePersonnes(bookingform.getNombrePersonnes());
b.setTotalAmount(bookingform.getTotal_amount());
b.setCheckInDate(bookingform.getCheck_in_date());
b.setCheckOutDate(bookingform.getCheck_out_date());
b.setCustomer(c);
b.setRoom(r);
bookingservice.update(b);

return "redirect:/bookings";
  }
  
  
}
