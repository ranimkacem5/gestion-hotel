package com.example.hotelversion2.DAO.entites;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.example.hotelversion2.Web.Models.Bookingstatus;
import com.example.hotelversion2.Web.Models.Paymentstatus;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Entity 
@Table(name = "Bookings") 
public class Booking {
    @Id   
    @GeneratedValue(strategy = GenerationType.IDENTITY)   
    private Long booking_id;  // Correction : camelCase

    @ManyToOne   
    @JoinColumn(name = "room_id", nullable = false)     
    private Room room;

    @ManyToOne()   
    @JoinColumn(name = "customer_id", nullable = false)    
    private Customer customer;

    @Column(name = "check_in_date", nullable = false)  // Correction : nom de colonne correct
    private LocalDate checkInDate;  // Correction : camelCase

    @Column(name = "check_out_date", nullable = false)
    private LocalDate checkOutDate;  // Correction : camelCase

    @Column(name = "payment_status", nullable = false)   
    @Enumerated(EnumType.STRING)   
    private Paymentstatus paymentStatus;  // Correction : camelCase

    @Column(name = "booking_status", nullable = false)   
    @Enumerated(EnumType.STRING)   
    private Bookingstatus bookingStatus;  // Correction : camelCase

    @Column(name = "nombre_personnes", nullable = false)  // Correction : snake_case recommandé pour les noms de colonnes
    private int nombrePersonnes;

    @Column(name = "total_amount", nullable = false)   
    private float totalAmount;  // Correction : camelCase
}