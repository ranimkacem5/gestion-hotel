package com.example.hotelversion2.Web.Controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.hotelversion2.Business.Services.BookingService;
import com.example.hotelversion2.Business.Services.Customerservice;
import com.example.hotelversion2.Business.Services.RoomServices;
import com.example.hotelversion2.DAO.Repository.CustommerRepository;
import com.example.hotelversion2.DAO.entites.Booking;
import com.example.hotelversion2.DAO.entites.Customer;
import com.example.hotelversion2.DAO.entites.Room;
import com.example.hotelversion2.Web.Models.RoomType;

@Controller
public class PageController {
    public final RoomServices rommservice;
    public final BookingService bookingservice ;
    public final Customerservice customerservice;
    public  PageController(RoomServices rommservice,BookingService bookingservice,Customerservice customerservice)
    {
this.rommservice=rommservice;
this.bookingservice=  bookingservice ; 
this.customerservice=customerservice; }

    @RequestMapping(path = "pagehome/contact", method = RequestMethod.GET)
    public String ContactPage() {
        return "contact";
    }
    @GetMapping("/")
public String ShowpageHome() {
 
    return ("homepage");
}
@GetMapping("/about")
public String Showpageabout() {
 
    return "about";
}
 @GetMapping("/clientpage")

    public String afficherChambres( @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "3") int pageSize,
    
    Model model) {
      Page<Room> pageRoom;

   
        // Pagination classique sans recherche
        pageRoom = this.rommservice.getAllRoomPagination(PageRequest.of(page, pageSize));
    

    model.addAttribute("rooms", pageRoom.getContent());
    model.addAttribute("pageSize", pageSize);
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", pageRoom.getTotalPages());
    
        return "liste-room-client";
    }
    @RequestMapping(path="client/filter", method=RequestMethod.GET)
    public String Filtrerrooms(    @RequestParam(required = false) RoomType roomType,
            @RequestParam(required = false) String view,
            @RequestParam(required = false, defaultValue = "asc") String sortByPrix,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int pageSize,
            Model model) {
               
                
                Page<Room> RoomPage;
            
                if (roomType == null && (view == null || view.isEmpty())) {
                    // Retourne la liste triée par prix si spécifié
                    RoomPage = this.rommservice.getRoomSortedByPricePagination(sortByPrix, PageRequest.of(page, pageSize));
                } 
                // Filtrer selon le type de chambre et la vue
                else if (roomType != null && view != null && !view.isEmpty()) {
                    RoomPage = this.rommservice.filterRoomsByTypeAndView(roomType, view, sortByPrix, PageRequest.of(page, pageSize));
                } 
                // Filtrer uniquement par type de chambre
                else if (roomType != null) {
                    RoomPage = this.rommservice.filterRoomsByType(roomType, sortByPrix, PageRequest.of(page, pageSize));
                } 
                // Filtrer uniquement par vue
                else if (view != null && !view.isEmpty()) {
                    RoomPage = this.rommservice.filterRoomsByView(view, sortByPrix, PageRequest.of(page, pageSize));
                } 
                else {
                    // Par défaut, retourner les chambres triées par prix
                    RoomPage = this.rommservice.getRoomSortedByPricePagination(sortByPrix, PageRequest.of(page, pageSize));
                }
            
            
                model.addAttribute("rooms", RoomPage.getContent());
                model.addAttribute("sortByPrix", sortByPrix);
                model.addAttribute("roomType", roomType);
                model.addAttribute("view", view);
                model.addAttribute("pageSize", pageSize);
                model.addAttribute("currentPage", page);
                model.addAttribute("totalPages", RoomPage.getTotalPages());
                

        return "liste-room-client";
    }
    @GetMapping(path="/admin")
    public String getAdminHomepage(Model model ) {
        List<Booking>bookings =bookingservice.getAllBookings();
        
        List<Room>rooms =rommservice.getAllRooms();
        List<Customer> custommers=customerservice.getallcustommers();
        int nbbookings=bookings.size();
        int nbrooms=rooms.size();
        int nbcustomers=custommers.size();
        model.addAttribute("nbrooms", nbrooms);
        
        model.addAttribute("nbbookings", nbbookings);
        model.addAttribute("nbcustomers", nbcustomers);
        return"admin_homepage";
    }

}