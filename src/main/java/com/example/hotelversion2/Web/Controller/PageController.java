package com.example.hotelversion2.Web.Controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.hotelversion2.Business.Services.RoomServices;
import com.example.hotelversion2.DAO.entites.Room;
import com.example.hotelversion2.Web.Models.RoomType;

@Controller
public class PageController {
    public final RoomServices rommservice;
    public  PageController(RoomServices rommservice)
    {
this.rommservice=rommservice;
    }

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
    @RequestParam(required = false) String roomName, 
    Model model) {
      Page<Room> pageRoom;

    if (roomName != null && !roomName.isEmpty()) {
        // Rechercher par nom de chambre
        pageRoom = this.rommservice.searchRoomsByName(roomName, PageRequest.of(page, pageSize));
    } else {
        // Pagination classique sans recherche
        pageRoom = this.rommservice.getAllRoomPagination(PageRequest.of(page, pageSize));
    }

    model.addAttribute("rooms", pageRoom.getContent());
    model.addAttribute("pageSize", pageSize);
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", pageRoom.getTotalPages());
    model.addAttribute("roomName", roomName);
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
            
                // Vérification si aucun filtre n'est sélectionné
                if ((roomType == null || roomType.toString().isEmpty()) && 
                    (view == null || view.isEmpty())) {
                    // Retourne à la liste initiale sans filtre
                    RoomPage = this.rommservice.getAllRoomPagination(PageRequest.of(page, pageSize));
                } 
                // Filtrer selon le type de chambre et la vue
                else if (roomType != null && view != null && !view.isEmpty()) {
                    RoomPage = this.rommservice.filterRoomsByTypeAndView(roomType, view, sortByPrix, PageRequest.of(page, pageSize));
                } 
                else if (roomType != null && (view == null || view.isEmpty())) {
                    RoomPage = this.rommservice.filterRoomsByType(roomType, sortByPrix, PageRequest.of(page, pageSize));
                } 
                else if ((roomType == null || roomType.toString().isEmpty()) && view != null && !view.isEmpty()) {
                    RoomPage = this.rommservice.filterRoomsByView(view, sortByPrix, PageRequest.of(page, pageSize));
                } 
                else {
                    // Utiliser le tri par prix si rien d'autre n'est spécifié
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
    

}