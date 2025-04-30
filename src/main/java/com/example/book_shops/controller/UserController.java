package com.example.book_shops.controller;

import com.example.book_shops.DTO.UserDto;
import com.example.book_shops.Exception.AlreadyExistsException;
import com.example.book_shops.Exception.ResourceNotFoundException;
import com.example.book_shops.request.CreateUserRequest;
import com.example.book_shops.request.UserUpdateRequest;
import com.example.book_shops.response.ApiResponse;
import com.example.book_shops.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import com.example.book_shops.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {
    // Injecting the IUserService which contains the business logic for user operations
    private final IUserService userService;

    // Endpoint for retrieving a user by their ID
    @GetMapping("/{userId}/user")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId) {
        try {
            // Fetching user from the service layer
            User user = userService.getUserById(userId);
            // Converting user to a UserDto for better API response structure
            UserDto userDto = userService.convertUserToDto(user);
            // Returning successful response with user data
            return ResponseEntity.ok(new ApiResponse("Success", userDto));
        } catch (ResourceNotFoundException e) {
            // Handling case where the user is not found
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // Endpoint for creating a new user
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request) {
        try {
            // Calling service method to create a new user
            User user = userService.createUser(request);
            // Converting the created user into a DTO
            UserDto userDto = userService.convertUserToDto(user);
            // Returning success response with the created user's details
            return ResponseEntity.ok(new ApiResponse("Create User Success!", userDto));
        } catch (AlreadyExistsException e) {
            // Handling case where the user already exists in the system
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // Endpoint for updating an existing user
    @PutMapping("/{userId}/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UserUpdateRequest request, @PathVariable Long userId) {
        try {
            // Updating user with provided request data
            User user = userService.updateUser(request, userId);
            // Converting the updated user to a DTO for better response structure
            UserDto userDto = userService.convertUserToDto(user);
            // Returning success response with updated user's details
            return ResponseEntity.ok(new ApiResponse("Update User Success!", userDto));
        } catch (ResourceNotFoundException e) {
            // Handling case where the user is not found
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    // Endpoint for deleting a user by their ID
    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {
        try {
            // Deleting the user from the service layer
            userService.deleteUser(userId);
            // Returning success response after successful deletion
            return ResponseEntity.ok(new ApiResponse("Delete User Success!", null));
        } catch (ResourceNotFoundException e) {
            // Handling case where the user is not found for deletion
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
