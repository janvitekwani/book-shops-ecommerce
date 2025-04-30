package com.example.book_shops.Exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException (String message){
        super(message);
    }

}
