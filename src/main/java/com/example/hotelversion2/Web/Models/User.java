package com.example.hotelversion2.Web.Models;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ; 
    @Column(name = "username", nullable = false)
    @NotEmpty
    @Size(min = 5, max = 30)
    private String username;
    @Column(name = "password", nullable = false)
    @NotEmpty
    @Size(min = 5, max = 30)
    private String password ;
    
   




}
