package com.example.hotelversion2.Web.Controller;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.example.hotelversion2.Business.Services.Customerservice;
import com.example.hotelversion2.Business.Services.RoomServices;
import com.example.hotelversion2.DAO.entites.Booking;
import com.example.hotelversion2.DAO.entites.Customer;
import com.example.hotelversion2.DAO.entites.Room;
import com.example.hotelversion2.Web.Models.CustomerForm;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Past;

/* Carte des opérations CRUD
 * 
 * Create - Get  /Rooms/create : Récupérer le forumulaire d'ajout d'une nouvelle chambre
 *        - Post /Rooms/create : Ajouter une personne à la liste chambre  
 * 
 * Read   - Get /clients :       Récupérer une liste de clients
 * 
 * Update  -Get /Rooms/{id}/edit  : Récuprer le formulaire de mise à jour de chambre
 *         -Post /Rooms/{id}/edit : Mettre à jour une chambre dans la liste chambre
 * 
 * Delete -Post /Rooms/{id}/delete  : supprimer une chambre de la liste chambre
 */

@Controller
@RequestMapping("/clients")
public class CustommerController {
    // The line `private final Customerservice customerservice;` in the Java code
    // snippet is declaring a private final instance variable named
    // `customerservice` of type `Customerservice`. This variable is used to hold a
    // reference to an instance of the `Customerservice` class, which is a service
    // class responsible for handling operations related to customers in the
    // application.
    private static final Logger logger = LoggerFactory.getLogger(RoomController.class);
    private final Customerservice customerservice;

    public CustommerController(@Autowired Customerservice customerservice) {
        this.customerservice = customerservice;
    }

    @GetMapping("")
    public String getallcustommers(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int pageSize,
            @RequestParam(required = false) String EmailCustomer,
            Model model) {
         Page<Customer> pageCustomer;
                if(EmailCustomer !=null&& !EmailCustomer.isEmpty())
                {
                    pageCustomer=this.customerservice.rechercherbyemail(EmailCustomer,PageRequest.of(page, pageSize));
                }
                else 
                {
                    pageCustomer=  customerservice.getAllCustomerPagination(PageRequest.of(page, pageSize));
                }
             
                model.addAttribute("clients", pageCustomer.getContent());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pageCustomer.getTotalPages());

        model.addAttribute("EmailCustomer", EmailCustomer);

        return "clients/clients";
    }
    @RequestMapping(path="/filter", method=RequestMethod.GET)
    public String getCustomerSorted(@RequestParam(required = false, defaultValue = "asc") String sortByEmail,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "3") int pageSize,Model model) {
        Page<Customer> customerPage =customerservice.getCustomerSortedByEmailPagination(sortByEmail, PageRequest.of(page, pageSize));

        model.addAttribute("clients", customerPage.getContent());
        model.addAttribute("sortByEmail", sortByEmail);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", customerPage.getTotalPages());

        return "clients/clients";
    }
    

    @RequestMapping(path = "/create", method = RequestMethod.GET)
    public String showAddCustomerForm(Model model) {
        model.addAttribute("customerForm", new CustomerForm());
        return "clients/add-client";
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public String requestMethodName(@Valid @ModelAttribute CustomerForm customerForm, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("error", "Invalide input");
            return "clients/add-client";
        }
        customerservice.add(new Customer(null, customerForm.getName(), customerForm.getLastName(),
                customerForm.getEmail(), customerForm.getContact(), customerForm.getAddress(), new ArrayList<>()));

        return "redirect:/clients";
    }

    @RequestMapping(path = "/{id}/delete", method = RequestMethod.POST)
    public String RemoveCustomer(@PathVariable Long id) {
        Customer c = customerservice.getCustomerbyId(id);
        logger.error(c.getAddress());

        customerservice.remove(c);
        return "redirect:/clients";
    }

    @RequestMapping(path = "/{id}/edit")
    public String showeditclientform(@PathVariable Long id, Model model) {
        Customer c = customerservice.getCustomerbyId(id);
        logger.error(c.getName());
        logger.error(c.getLastName());

        logger.error(c.getEmail());
        model.addAttribute("CustomerForm",
                new CustomerForm(c.getName(), c.getLastName(), c.getEmail(), c.getContact(), c.getAddress()));
        return "clients/edit-client";

    }

    @RequestMapping(path = "{id}/edit", method = RequestMethod.POST)
    public String requestMethodName(@PathVariable Long id, Model model,
            @Valid @ModelAttribute("CustomerForm") CustomerForm CustomerForm, BindingResult br) {
        if (br.hasErrors()) {
            model.addAttribute("error", "Invalid   Input");
            return "clients/edit-client";
        }
        Customer c = customerservice.getCustomerbyId(id);
        c.setName(CustomerForm.getName());
        c.setLastName(CustomerForm.getLastName());
        c.setEmail(CustomerForm.getEmail());
        c.setContact(CustomerForm.getContact());
        c.setAddress(CustomerForm.getAddress());
        customerservice.update(c);
        return "redirect:/clients";
    }

}