package com.example.hotelversion2.Web.Models;



import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingForm {


    // Validation pour le client
    @NotNull(message = "L'identifiant du client est obligatoire")
    private Long customerId;

    // Liste des chambres
    @NotNull(message = "Sélectionnez au moins une chambre")
    private Long  roomId;

    // Date d'arrivée
    @NotNull(message = "La date d'arrivée est obligatoire")
    @Future(message = "La date d'arrivée doit être dans le futur")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate check_in_date;

    // Date de départ
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "La date de départ est obligatoire")
    private LocalDate check_out_date;

    // Validation de la date de départ par rapport à la date d'arrivée
   /*  public boolean isValidDateRange() {
        return check_out_date != null && check_in_date != null 
               && check_out_date.isAfter(check_in_date);
    }*/

    // Statut de paiement
    @NotNull(message = "Le statut de paiement est obligatoire")
    private Paymentstatus payment_status;

    // Statut de réservation
    @NotNull(message = "Le statut de réservation est obligatoire")
    private Bookingstatus booking_status;

    // Nombre de personnes
    @NotNull(message = "Le nombre de personnes est obligatoire")
    @Min(value = 1, message = "Le nombre de personnes doit être au moins 1")
    @Max(value = 5, message = "Le nombre de personnes ne peut pas dépasser 10")
    private int nombrePersonnes;

    // Montant total
    @NotNull(message = "Le montant total est obligatoire")
    @Positive(message = "Le montant total doit être positif")
    @DecimalMin(value = "0.01", message = "Le montant total doit être supérieur à 0")
    private float total_amount;
}