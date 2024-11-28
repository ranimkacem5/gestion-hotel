package com.example.hotelversion2.DAO.Models;

import java.util.List;
import jakarta.persistence.*;
@Entity
@Table(name="customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name="prenom",nullable = false)
    String Name;
    @Column(name="nom",nullable = false)
    String LastName;
    @Column(name="email",nullable = false )
    String Email;
    @Column(name="contact")
    String Contact;
    @Column(name="adress")
    String Adress;
    @OneToMany(mappedBy = "customer")
    private  List <Booking> booking_history ;
    @Enumerated(EnumType.STRING)
    @Column(name="methode_payment " ,nullable = false)
    TypePaiement method_Paiement;


   

    
}
