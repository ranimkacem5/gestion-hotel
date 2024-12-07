package com.example.hotelversion2.DAO.entites;

import java.util.Date;

import com.example.hotelversion2.Web.Models.TypePaiement;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payment")
public class Payment {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long paymentId; 
  
  @ManyToOne 
  //@JoinColumn(name = "booking_id", nullable = false) // Foreign key to the Booking entity
  private Booking booking;
  
  @Column(name = "date_payment", nullable = false)
  private Date paymentDate; 
  
  @Column(name = "methode_payment", nullable = false) 
  @Enumerated(EnumType.STRING)
  private TypePaiement methodPayment; 
}
