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
  private Long Booking_id;
  @ManyToOne
  @JoinColumn(name = "room_id", nullable = false)  
  private Room room; 
  @ManyToOne()
  @JoinColumn(name = "customer_id", nullable = false) 
  private Customer customer;
  @Column(name=" check_in_date;",nullable = false)
  private LocalDate check_in_date;
  @Column(name="check_out_date",nullable = false)
  private LocalDate check_out_date;
  @Column(name ="payment_status",nullable = false)
  @Enumerated(EnumType.STRING)
  private Paymentstatus payment_status;
  @Column(name="Booking_status",nullable = false)
  @Enumerated(EnumType.STRING)
  private Bookingstatus Booking_status ;
  @Column(name="nombrePersonnes",nullable = false)
  private int nombrePersonnes;
  @Column(name = "total_amount", nullable = false)
  private float total_amount;
 // @Column(name ="typePaiement",nullable = false)
 // @Enumerated(EnumType.STRING)
  //private TypePaiement typePaiement;

  
}
