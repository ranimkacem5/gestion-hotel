package com.example.hotelversion2.DAO.entites;

import java.util.List;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter()
@Setter()
@AllArgsConstructor()
@NoArgsConstructor()
@Entity
@Table(name="customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name="Name",nullable = false)
    private  String Name;
    @Column(name="LastName",nullable = false)
    private  String LastName;
    @Column(name="Email",nullable = false )
    private String Email;
    @Column(name="Contact" ,nullable  =false)
    private String Contact;
    @Column(name="Address",nullable = false)
    private String Address;
    @OneToMany(mappedBy = "customer"  ,cascade = CascadeType.ALL,orphanRemoval = true)
    private  List <Booking> booking_history ;
 


   

    
}
