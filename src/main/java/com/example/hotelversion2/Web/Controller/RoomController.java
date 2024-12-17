package com.example.hotelversion2.Web.Controller;
import java.nio.file.Paths;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.multipart.MultipartFile;

import com.example.hotelversion2.Business.Services.BookingService;
import com.example.hotelversion2.Business.Services.RoomServices;
import com.example.hotelversion2.DAO.entites.Room;
import com.example.hotelversion2.Web.Models.RoomForm;
import com.example.hotelversion2.Web.Models.RoomType;

import org.springframework.web.bind.annotation.PathVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/* Carte des opérations CRUD
 * 
 * Create - Get  /Rooms/create : Récupérer le forumulaire d'ajout d'une nouvelle chambre
 *        - Post /Rooms/create : Ajouter une personne à la liste chambre  
 * 
 * Read   - Get /Rooms :       Récupérer une liste de chambres
 * 
 * Update  -Get /Rooms/{id}/edit  : Récuprer le formulaire de mise à jour de chambre
 *         -Post /Rooms/{id}/edit : Mettre à jour une chambre dans la liste chambre
 * 
 * Delete -Post /Rooms/{id}/delete  : supprimer une chambre de la liste chambre
 */

@Controller
public class RoomController {
    public final RoomServices rommservice;
    public final BookingService bookingService;
    private static final Logger logger = LoggerFactory.getLogger(RoomController.class);




    // System.getProperty("user.dir") : Renvoie le chemin du répertoire courant où
    // l'application s'exécute (souvent la racine du projet en mode développement).
    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/images";
    @Autowired
    public RoomController(RoomServices roomservice ,BookingService bookingService) {
        this.rommservice =roomservice;
        this.bookingService=bookingService;

    }
   /*  @GetMapping("/rooms/filter")
    public String filterRooms(@RequestParam(required = false, defaultValue = "asc") String sortByPrix, Model model) {
        // Récupérer la liste des chambres triée
        List<Room> rooms = rommservice.getRoomSortedByPrice(sortByPrix);
        model.addAttribute("rooms", rooms);
        model.addAttribute("sortByPrix", sortByPrix); // Maintenir l'état du filtre
        return "list"; // Vue Thymeleaf pour afficher les chambres
    }*/

   
/*@GetMapping("/")
public String ShowpageHome() {
 
    return ("homepage");
}*/
/* 
    @GetMapping("/rooms")
    public String getAllRooms(@RequestParam (defaultValue="0") int page,
     @RequestParam (defaultValue="3")int pagesize ,
     @RequestParam(required = false) String roomName,
     Model model)
      {
        Page<Room>pageroom=this.rommservice.searchRoomsByName(roomName,PageRequest.of(page, pagesize));
        model.addAttribute("rooms", pageroom.getContent());
        model.addAttribute("pageSize", pagesize);
        model.addAttribute("currentPage", page);
        model.addAttribute("roomName", roomName);
        model.addAttribute("totalPages", pageroom.getTotalPages());
        return "rooms/list-rooms";*/


   /*  @GetMapping("/rooms")
    public String getAllRooms(@RequestParam (defaultValue="0") int page,
     @RequestParam (defaultValue="3")int pagesize ,
     Model model)
      {
        Page<Room>pageroom=this.rommservice.getAllRoomPagination(PageRequest.of(page, pagesize));
        model.addAttribute("rooms", pageroom.getContent());
        model.addAttribute("pageSize", pagesize);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pageroom.getTotalPages());
        return "rooms/list-rooms";
    }*/
     @GetMapping("/rooms")
public String getAllRooms(
        @RequestParam(defaultValue = "0") int page,
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

    return "rooms/list-rooms";
}

    /*@RequestMapping("/rooms/filter")
    public String getPersonSorted(@RequestParam(required = false, defaultValue = "asc") String sortByPrix,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int pageSize,
            Model model) {
        Page<Room> RoomPage = this.rommservice.getRoomSortedByPricePagination(sortByPrix,
                PageRequest.of(page, pageSize));
        model.addAttribute("rooms", RoomPage.getContent());
        model.addAttribute("sortByAge", sortByPrix);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", RoomPage.getTotalPages());
        return "rooms/list-rooms";
    }*/
    @RequestMapping("/rooms/filter")
    public String filterRooms(
            @RequestParam(required = false) RoomType roomType,
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
        
        return "rooms/list-rooms";
    }
    /*@RequestMapping("/rooms/filter")
public String filterRooms(
        @RequestParam(required = false) RoomType roomType,
        @RequestParam(required = false) String view,
        @RequestParam(required = false, defaultValue = "asc") String sortByPrix,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "3") int pageSize,
        Model model) {
    
    Page<Room> RoomPage;

    // Filtrer selon le type de chambre et la vue
    if (roomType != null  && view != null && !view.isEmpty()) {
        RoomPage = this.rommservice.filterRoomsByTypeAndView(roomType, view, sortByPrix, PageRequest.of(page, pageSize));
    } else if (roomType != null&& view == null && view.isEmpty() ) {
        RoomPage = this.rommservice.filterRoomsByType(roomType, sortByPrix, PageRequest.of(page, pageSize));
    } else if (roomType==null && view != null && !view.isEmpty()) {
        RoomPage = this.rommservice.filterRoomsByView(view, sortByPrix, PageRequest.of(page, pageSize));
}
     else {
        // Si aucun filtre n'est appliqué, utiliser le tri par prix
        RoomPage = this.rommservice.getRoomSortedByPricePagination(sortByPrix, PageRequest.of(page, pageSize));
    }

    model.addAttribute("rooms", RoomPage.getContent());
    model.addAttribute("sortByPrix", sortByPrix);
    model.addAttribute("roomType", roomType);
    model.addAttribute("view", view);
    model.addAttribute("pageSize", pageSize);
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", RoomPage.getTotalPages());
    
    return "rooms/list-rooms";
}*/

    // methode get qui va afficher le formulaire d'ajout
    @RequestMapping("/rooms/create")
    public String showAddRoomForm(Model model) {
        model.addAttribute("roomForm", new RoomForm());
        return "rooms/add-room";
    }

    @RequestMapping(path = "/rooms/create" ,method = RequestMethod.POST)
    public String addroom(@Valid @ModelAttribute RoomForm roomForm, BindingResult br, @RequestParam MultipartFile file,
            Model model) {
               
                if (br.hasErrors()) {
                    model.addAttribute("error", "form invalide");
                    return "rooms/add-room";
                }
        if (!file.isEmpty()) { 
             logger.error("avant 'l'insertion d'une image 1");
        StringBuilder fileName = new StringBuilder();
        logger.error("avant 'l'insertion d'une image 2");
        fileName.append(file.getOriginalFilename());
        logger.error("avant 'l'insertion d'une image 3 ");
        Path newFilePath = Paths.get(uploadDirectory, fileName.toString());
        logger.error("avant 'l'insertion d'une image 4");
        try {
            logger.error("avant 'l'insertion d'une image5 ");
            Files.write(newFilePath, file.getBytes());
            logger.error("apres 'l'insertion d'une image 6");
        } catch (Exception e) {
            logger.error("Erreur lors de l'enregistrement du fichier : ", e);
            e.printStackTrace();
        }
        this.rommservice.addRoom(new Room(null,roomForm.getName(), roomForm.getStatus(), roomForm.getView(), roomForm.getRoomType(), roomForm.getEquipements(),
        roomForm.getCapacity(), roomForm.getPricePerNight(), fileName.toString(),null));

    } else {
        logger.error("image est vide  ");
        this.rommservice.addRoom(new Room(null,roomForm.getName(), roomForm.getStatus(), roomForm.getView(), roomForm.getRoomType(), roomForm.getEquipements(),
        roomForm.getCapacity(), roomForm.getPricePerNight(), null,null));
    }
       
        

        return "redirect:/rooms";

    }
    

    @RequestMapping(path = "/rooms/{id}/edit")
    public String geteditform(@PathVariable Long id, Model model) {
        Room r = rommservice.getRoomById(id);
        model.addAttribute("roomForm", new RoomForm(r.getName(),r.getStatus(), r.getView(), r.getRoomType(), r.getEquipments(),
                r.getCapacity(), r.getPricePerNight(), null));
        return "rooms/edit-room";
    }

    @RequestMapping(path = "/rooms/{id}/edit" ,method = RequestMethod.POST)
    public String edit_room(@PathVariable long id, @Valid @ModelAttribute RoomForm rf, BindingResult bresult,
            @RequestParam MultipartFile file, Model model) {
        Room r = rommservice.getRoomById(id);
        if (bresult.hasErrors()) {
            model.addAttribute("error", "form invalide");
            return "rooms/edit-room";
        }
        r.setName(rf.getName());
         r.setCapacity(rf.getCapacity());
         r.setEquipments(rf.getEquipements());
         r.setPricePerNight(rf.getPricePerNight());
         r.setRoomType(rf.getRoomType());
         r.setStatus(rf.getStatus());
         r.setView(rf.getView());
        if (!file.isEmpty()) {
            StringBuilder fileName = new StringBuilder();
            fileName.append(file.getOriginalFilename());
            // Paths.get() : Méthode de la classe java.nio.file.Paths pour combiner des
            // morceaux de chemin.
            // uploadDirectory : Répertoire cible pour le fichier.
            // file.getOriginalFilename() : Récupère le nom original du fichier final
            // téléchargé.
            Path newFilePath = Paths.get(uploadDirectory, fileName.toString());

            try {
                Files.write(newFilePath, file.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
            // supprimer ancienne photo
            if (r.getImages() != null) {
                Path pathimage = Paths.get(uploadDirectory, r.getImages());
                try {
                    Files.deleteIfExists(pathimage);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            r.setImages( fileName.toString());
          

        } 

            this.rommservice
                    .updateRoom(r);
        

        return "redirect:/rooms";
    }

    @RequestMapping(path = "/rooms/{id}/delete", method = RequestMethod.POST)
    public String delete(@PathVariable Long id) {
        //logger.error("id =",id);
        
        Room r = rommservice.getRoomById(id);
        if (r.getImages() != null) {
            Path filepath = Paths.get(uploadDirectory, r.getImages());
            try {
                Files.deleteIfExists(filepath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        rommservice.deleteRoom(r);

        return "redirect:/rooms";
    }

}