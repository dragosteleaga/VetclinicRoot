package com.example.vetclinic.controller.api;

import com.example.vetclinic.dto.RegisterRequest;
import com.example.vetclinic.dto.UserResponse;
import com.example.vetclinic.model.Role;
import com.example.vetclinic.model.User;
import com.example.vetclinic.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "Endpoints for CRUD user")

public class UserController {
    @Autowired
    UserService userService;
    @GetMapping
    public ResponseEntity<List<UserResponse>>getAllUsers(){
        List<UserResponse> users=userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse>getUserById(@PathVariable Long id){
        UserResponse user= userService.getUserById(id);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
    // Update an existing user
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody RegisterRequest registerRequest) {
        UserResponse updatedUser = userService.updateUser(id, registerRequest);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    // Delete a user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/getRoleByEmail/{email}")
    public Role getRoleByEmail(@PathVariable("email") String email){
        System.out.println("email"+email);
        Role role=userService.getRoleByEmail(email);
        return role;
    }
}
