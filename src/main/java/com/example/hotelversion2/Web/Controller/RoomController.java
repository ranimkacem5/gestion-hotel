package com.example.hotelversion2.Web.Controller;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.example.hotelversion2.Business.Services.RoomServices;

import com.example.hotelversion2.DAO.Models.Room;
import com.example.hotelversion2.DAO.Models.RoomForm;


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
    private static final Logger logger = LoggerFactory.getLogger(RoomController.class);




    // System.getProperty("user.dir") : Renvoie le chemin du répertoire courant où
    // l'application s'exécute (souvent la racine du projet en mode développement).
    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/images";
    @Autowired
    public RoomController(RoomServices roomservice) {
        this.rommservice =roomservice;
    }
@GetMapping("/")
public String ShowpageHome() {
    return ("homepage");
}

    @GetMapping("/rooms")
    public String getAllRooms(Model model) {
        model.addAttribute("rooms", this.rommservice.getAllRooms());
        return "list";
    }

    // methode get qui va afficher le formulaire d'ajout
    @RequestMapping("/rooms/create")
    public String showAddRoomForm(Model model) {
        model.addAttribute("roomForm", new RoomForm());
        return "add-room";
    }

    @RequestMapping(path = "/rooms/create" ,method = RequestMethod.POST)
    public String addroom(@Valid @ModelAttribute RoomForm roomForm, BindingResult br, @RequestParam MultipartFile file,
            Model model) {
               
                if (br.hasErrors()) {
                    model.addAttribute("error", "form invalide");
                    return "add-room";
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
        this.rommservice.addRoom(new Room(null, roomForm.getStatus(), roomForm.getView(), roomForm.getRoomType(), roomForm.getEquipements(),
        roomForm.getCapacity(), roomForm.getPricePerNight(), fileName.toString()));

    } else {
        logger.error("image est vide  ");
        this.rommservice.addRoom(new Room(null, roomForm.getStatus(), roomForm.getView(), roomForm.getRoomType(), roomForm.getEquipements(),
        roomForm.getCapacity(), roomForm.getPricePerNight(), null));
    }
       
        

        return "redirect:/rooms";

    }

    @RequestMapping(path = "/rooms/{id}/edit")
    public String geteditform(@PathVariable Long id, Model model) {
        Room r = rommservice.getRoomById(id);
        model.addAttribute("roomForm", new RoomForm(r.getStatus(), r.getView(), r.getRoomType(), r.getEquipments(),
                r.getCapacity(), r.getPricePerNight(), null));
        return "edit-room";
    }

    @RequestMapping(path = "/rooms/{id}/edit" ,method = RequestMethod.POST)
    public String edit_room(@PathVariable long id, @Valid @ModelAttribute RoomForm rf, BindingResult bresult,
            @RequestParam MultipartFile file, Model model) {
        Room r = rommservice.getRoomById(id);
        if (bresult.hasErrors()) {
            model.addAttribute("error", "form invalide");
            return "edit-room";
        }
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
        rommservice.deleteRoomById(id);

        return "redirect:/rooms";
    }

}
