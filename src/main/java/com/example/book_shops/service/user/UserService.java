package com.example.book_shops.service.user;


import com.example.book_shops.DTO.UserDto;
import com.example.book_shops.Exception.AlreadyExistsException;

import com.example.book_shops.Exception.ResourceNotFoundException;
import com.example.book_shops.repository.UserRepository;
import com.example.book_shops.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.example.book_shops.model.User;
import com.example.book_shops.request.CreateUserRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository; // Repository for user data access
    private final ModelMapper modelMapper; // Mapper for converting User to UserDto

    /**
     * Retrieves a user by their unique ID.
     *
     * @param userId the ID of the user to retrieve
     * @return the User object corresponding to the provided ID
     * @throws ResourceNotFoundException if no user is found with the given ID
     */
    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }

    /**
     * Creates a new user with the provided details.
     * Ensures that the email is unique before creating the user.
     *
     * @param request the details of the user to create
     * @return the created User object
     * @throws AlreadyExistsException if a user with the same email already exists
     */
    @Override
    public User createUser(CreateUserRequest request) {
        // Check if a user with the provided email already exists
        return Optional.of(request)
                .filter(req -> !userRepository.existsByEmail(req.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setEmail(req.getEmail());
                    user.setPassword(req.getPassword());
                    user.setFirstName(req.getFirstName());
                    user.setLastName(req.getLastName());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new AlreadyExistsException("Oops! " + request.getEmail() + " already exists!"));
    }

    /**
     * Updates an existing user's details.
     *
     * @param request  the new details to update
     * @param userId   the ID of the user to update
     * @return the updated User object
     * @throws ResourceNotFoundException if no user is found with the given ID
     */
    @Override
    public User updateUser(UserUpdateRequest request, Long userId) {
        return userRepository.findById(userId)
                .map(existingUser -> {
                    existingUser.setFirstName(request.getFirstName());
                    existingUser.setLastName(request.getLastName());
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }

    /**
     * Deletes a user by their unique ID.
     *
     * @param userId the ID of the user to delete
     * @throws ResourceNotFoundException if no user is found with the given ID
     */
    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId)
                .ifPresentOrElse(userRepository::delete,
                        () -> {
                            throw new ResourceNotFoundException("User not found!");
                        });
    }

    /**
     * Converts a User entity to a UserDto.
     *
     * @param user the User entity to convert
     * @return the corresponding UserDto
     */
    @Override
    public UserDto convertUserToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

}