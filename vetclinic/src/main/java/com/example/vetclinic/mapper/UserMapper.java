package com.example.vetclinic.mapper;
import com.example.vetclinic.dto.RegisterRequest;
import com.example.vetclinic.dto.UserResponse;
import com.example.vetclinic.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toEntity(RegisterRequest registerRequest) {
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(registerRequest.getPassword());
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setRole(registerRequest.getRole());
        return user;
    }
    public UserResponse toResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        return response;
    }
}
