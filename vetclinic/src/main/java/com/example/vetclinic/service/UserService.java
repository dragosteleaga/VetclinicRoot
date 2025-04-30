package com.example.vetclinic.service;

import com.example.vetclinic.dto.RegisterRequest;
import com.example.vetclinic.dto.UserResponse;
import com.example.vetclinic.mapper.UserMapper;
import com.example.vetclinic.model.Role;
import com.example.vetclinic.model.User;
import com.example.vetclinic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserMapper userMapper;
    public List<UserResponse> getAllUsers(){
        List<User>users=userRepository.findAll();
        return users.stream().map(userMapper::toResponse)
                .collect(Collectors.toList());
    }
    // Method to get a user by their ID
    public UserResponse getUserById(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            return userMapper.toResponse(userOpt.get());
        } else {
            // Handle user not found, maybe throw an exception
            throw new RuntimeException("User not found with id: " + id);
        }
    }
    // Method to update an existing user
    public UserResponse updateUser(Long id, RegisterRequest registerRequest) {
        Optional<User> existingUserOpt = userRepository.findById(id);
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();

            // Update fields from the RegisterRequest
            existingUser.setEmail(registerRequest.getEmail());
            existingUser.setPassword(registerRequest.getPassword());
            existingUser.setFirstName(registerRequest.getFirstName());
            existingUser.setLastName(registerRequest.getLastName());
            existingUser.setRole(registerRequest.getRole());

            // Save the updated user to the repository
            User updatedUser = userRepository.save(existingUser);

            // Return the updated user as a UserResponse
            return userMapper.toResponse(updatedUser);
        } else {
            // Handle user not found, maybe throw an exception
            throw new RuntimeException("User not found with id: " + id);
        }
    }
    // Method to delete a user by their ID
    public void deleteUser(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            userRepository.delete(userOpt.get());
        } else {
            // Handle user not found, maybe throw an exception
            throw new RuntimeException("User not found with id: " + id);
        }
    }
    public Role getRoleByEmail(String email){
        Optional<User> userOpt = userRepository.findByEmail(email);

        return userOpt.get().getRole();
    }


}
