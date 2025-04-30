package com.example.book_shops.service.user;

import com.example.book_shops.DTO.UserDto;
import com.example.book_shops.request.CreateUserRequest;
import com.example.book_shops.request.UserUpdateRequest;
import com.example.book_shops.model.User;

public interface IUserService {

    User getUserById(Long userId); // get the user by id
    User createUser(CreateUserRequest request);  // create the user
    User updateUser(UserUpdateRequest request, Long userId);  // update user
    void deleteUser(Long userId); // delete user

    UserDto convertUserToDto(User user);
}
