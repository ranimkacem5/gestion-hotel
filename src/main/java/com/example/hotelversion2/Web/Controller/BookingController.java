package com.example.hotelversion2.Web.Controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
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

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


  
    @GetMapping("")
    public String getallBooking(@RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "3") int pageSize,
    @RequestParam(required = false) LocalDate checkInDate,
    Model model) {

      Page<Booking> pagebooking;
      if(checkInDate !=null)
      {
        pagebooking=bookingservice.rechercherbycheckInDate(checkInDate,PageRequest.of(page, pageSize));
      }
      else 
      {
        pagebooking=  bookingservice.getAllBookingPagination(PageRequest.of(page, pageSize));
      }
      model.addAttribute("bookings", pagebooking.getContent());
      model.addAttribute("pageSize", pageSize);
      model.addAttribute("currentPage", page);
      model.addAttribute("totalPages", pagebooking.getTotalPages());
      model.addAttribute("checkInDate", checkInDate);
        return  "bookings/list-booking";
    }
    /* 
  @RequestMapping(path="", method=RequestMethod.GET)
  public String ListBooking(Model model) {
    model.addAttribute("bookings",bookingservice.getAllBookings() );
      return "bookings/list-booking";
  }  */

  @RequestMapping(path="/filter", method=RequestMethod.GET)
    public String getCustomerSorted(@RequestParam(required = false, defaultValue = "asc") String  sortBycheckInDate,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "3") int pageSize,Model model) {
        Page<Booking> bookingPage =bookingservice.getBookingSortedBycheckInDatePagination(sortBycheckInDate, PageRequest.of(page, pageSize));

        model.addAttribute("bookings", bookingPage.getContent());
        model.addAttribute("sortBycheckInDate", sortBycheckInDate);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", bookingPage.getTotalPages());

        return "bookings/list-booking";
    }
    

  @RequestMapping(path="/create", method=RequestMethod.GET)
  public String showaddbookingForm(Model model) {
    model.addAttribute("bookingForm", new BookingForm());
    model.addAttribute("rooms",roomServices.getAllRooms());
    model.addAttribute("payment_status1",   Paymentstatus.values());
     model.addAttribute("booking_status1", Bookingstatus.values());
    model.addAttribute("customers", customerservice.getallcustommers());
      return "bookings/add-booking";
  }

  @RequestMapping(path="/create", method=RequestMethod.POST)
  public String create (@Valid @ModelAttribute("bookingForm") BookingForm bookingForm,BindingResult br,Model model) {
    if(br.hasErrors())
    {
      model.addAttribute("rooms",roomServices.getAllRooms());
    model.addAttribute("payment_status1",   Paymentstatus.values());
     model.addAttribute("booking_status1", Bookingstatus.values());
    model.addAttribute("customers", customerservice.getallcustommers());
       model.addAttribute("error", "Invalide input");  
      return "bookings/add-booking";}

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
   model.addAttribute("bookingForm", bookingform);  
   model.addAttribute("id", id);
   model.addAttribute("rooms", roomServices.getAllRooms());
   model.addAttribute("customers", customerservice.getallcustommers());
   model.addAttribute("payment_status1", Paymentstatus.values());
   model.addAttribute("booking_status1", Bookingstatus.values());
   return "bookings/edit-booking";
  }

  @RequestMapping(path="/{id}/edit", method=RequestMethod.POST)
 public String edit(@PathVariable Long id ,@Valid @ModelAttribute BookingForm bookingform, BindingResult br,Model model) {
      if(br.hasErrors())
    {
      model.addAttribute("rooms", roomServices.getAllRooms());
      model.addAttribute("customers", customerservice.getallcustommers());
      model.addAttribute("payment_status1", Paymentstatus.values());
      model.addAttribute("booking_status1", Bookingstatus.values());
         model.addAttribute("error","invalide input");
      
      return"bookings/edit-booking";
    }
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
