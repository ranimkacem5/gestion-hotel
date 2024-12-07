package com.example.hotelversion2.Web.Models;



import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerForm {
  
    @NotBlank(message = "Le prénom est obligatoire")
    private String Name;

    @NotBlank(message = "Le nom est obligatoire")
    private String LastName;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    private String Email;

    @NotBlank(message = "Le contact est obligatoire")
    private String Contact;

    @NotBlank(message = "L'adresse est obligatoire")
    private String Address;
}