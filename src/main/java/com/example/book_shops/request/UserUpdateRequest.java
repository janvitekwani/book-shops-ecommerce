package com.example.book_shops.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Data
public class UserUpdateRequest {
    private String firstName;  // First name of the user

    private String lastName;  // Last name of the user
}
