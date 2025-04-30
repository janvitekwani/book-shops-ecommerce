package com.example.book_shops.request;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String firstName;  // First name of the user

    private String lastName;  // Last name of the user

    private String email;  // Email address of the user

    private String password;  // Password for the user account
}
