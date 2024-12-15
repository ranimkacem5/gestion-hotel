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
    @Column(name="name",nullable = false)
    private  String name;
    @Column(name="LastName",nullable = false)
    private  String LastName;
    @Column(name="email",nullable = false )
    private String email;
    @Column(name="contact" ,nullable  =false)
    private String contact;
    @Column(name="Address",nullable = false)
    private String address;
    @OneToMany(mappedBy = "customer"  ,cascade = CascadeType.ALL,orphanRemoval = true)
    private  List <Booking> booking_history ;
 


   

    
}
