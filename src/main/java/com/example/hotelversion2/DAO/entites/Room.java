package com.example.hotelversion2.DAO.entites;

import java.util.List; // Corrigé pour utiliser java.util.List

import com.example.hotelversion2.Web.Models.RoomType;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;  

 @Column(name="name",nullable = false)
 private String name;
    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "view", nullable = false)  
    private String view;

    @Column(name = "roomType", nullable = false)
    @Enumerated(EnumType.STRING)
    private RoomType roomType;  
   
    @Column(name = "equipments" ,nullable = false)
    private String equipments;  

    @Column(name = "capacity", nullable = false)
    @Min(value = 1)
    @Max(value = 5)
    private int capacity;  

    @Column(name = "pricePerNight", nullable = false)  
    private double pricePerNight; 
    @Column(name = "images")
    private String images;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
private List<Booking> bookings;
  
}
