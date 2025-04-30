package com.example.book_shops.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;  // Unique identifier for the user

 private String firstName;  // First name of the user

 private String lastName;  // Last name of the user

 @NaturalId
 private String email;  // Email address (used as unique identifier)

 private String password;  // User's password

 @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
 private Cart cart;  // Cart associated with the user

 @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
 private List<Order> orders;  // Orders placed by the user
}