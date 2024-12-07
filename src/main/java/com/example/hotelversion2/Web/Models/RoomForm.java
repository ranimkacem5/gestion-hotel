package com.example.hotelversion2.Web.Models;

import java.util.List;

import jakarta.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoomForm {
    @NotBlank(message = "status obligatoir")
    private String status;
    @NotBlank(message = "View obligatoir")
    private String view;
    @NotNull(message = "Le type de paiement est obligatoire")
    private RoomType roomType;
    @NotBlank
    private String equipements;
    @NotNull
    @Min(value = 1)
    @Max(6)
    private int capacity;
    //@Positive pour garantir une valeur valide
    @Positive 
    @NotNull(message = "Le prix par nuit est obligatoire")
    @DecimalMin(value = "0.01", message = "Le prix par nuit doit être supérieur à 0")
    private double pricePerNight;
  
    private String images ;
    //private List<String> images;

}
