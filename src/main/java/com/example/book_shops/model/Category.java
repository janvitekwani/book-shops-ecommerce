package com.example.book_shops.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

     @JsonIgnore   // this is used to avoid the loop which is occuring when quantity is calling other
    @OneToMany(mappedBy ="category")   // when we are calling u then product should not carry it
    private List<Product> products;

    public Category(String name) {
        this.name = name;
    }

}
