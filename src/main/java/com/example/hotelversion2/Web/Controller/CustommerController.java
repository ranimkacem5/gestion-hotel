package com.example.hotelversion2.Web.Controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
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
    // The line `private final Customerservice customerservice;` in the Java code snippet is declaring a private final instance variable named `customerservice` of type `Customerservice`. This variable is used to hold a reference to an instance of the `Customerservice` class, which is a service class responsible for handling operations related to customers in the application.
   
    private final Customerservice customerservice ;
    public CustommerController(@Autowired Customerservice customerservice )
    {
this.customerservice=customerservice;
    }
@GetMapping("")
public String getallcustommers(Model model) {
    model.addAttribute("clients",customerservice.getallcustommers());
    return "clients/clients";
}
@RequestMapping(path="/create", method=RequestMethod.GET)
public String showAddCustomerForm(Model model) {
model.addAttribute("customerForm", new CustomerForm());
    return "clients/add-client";
}

@RequestMapping(path="/create",method = RequestMethod.POST)
public String requestMethodName(@Valid @ModelAttribute  CustomerForm customerForm,BindingResult br  ,Model model) {
if(br.hasErrors())
{ 
  model.addAttribute("error", "Invalide input");  
  return "clients/add-client";
}
customerservice.add(new Customer(null,customerForm.getName(),customerForm.getLastName(),customerForm.getEmail(),customerForm.getContact(),customerForm.getAddress(),new ArrayList<>()));


    return "redirect:/clients";
}
@RequestMapping(path="/{id}/delete", method=RequestMethod.POST)
public String RemoveCustomer(@PathVariable Long id ) {
    Customer c=customerservice.getCustomerbyId(id);
    customerservice.remove(c);
    return "redirect:/clients";
}


 @RequestMapping(path="/{id}/edit")
 public String showeditclientform(@PathVariable Long id ,Model model ) {
    Customer c =customerservice.getCustomerbyId(id);
    model.addAttribute("customerForm", new CustomerForm(c.getName(),c.getLastName(),c.getEmail(),c.getContact(),c.getAddress()));
   model.addAttribute("id", id);
return"clients/edit-client";

}
@RequestMapping(path="{id}/edit", method=RequestMethod.POST)
public String requestMethodName(@PathVariable Long id ,Model model , @ModelAttribute CustomerForm customerForm,BindingResult br ) {
if (br.hasErrors())
{model.addAttribute("error","Invalid   Input");
    return"clients/edit-client";
}
Customer c =customerservice.getCustomerbyId(id);
c.setName(customerForm.getName());
c.setLastName(customerForm.getLastName());
c.setEmail(customerForm.getEmail());
c.setContact(customerForm.getContact());
customerservice.update(c);
    return "redirect:/clients";
}

}