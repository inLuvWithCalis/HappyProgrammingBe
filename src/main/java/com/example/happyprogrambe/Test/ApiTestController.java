package com.example.happyprogrambe.Test;

import com.example.happyprogrambe.Domain.User;
import com.example.happyprogrambe.Repository.UserRepository;
import com.example.happyprogrambe.Repository.RoleRepository;
import com.example.happyprogrambe.Service.IdGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "*")
public class ApiTestController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private IdGeneratorService idGeneratorService;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/roles")
    public Object getAllRoles() {
        return roleRepository.findAll();
    }

    @GetMapping("/next-user-id")
    public String getNextUserId() {
        return idGeneratorService.generateUserId();
    }

    @GetMapping("/database-status")
    public String checkDatabaseConnection() {
        try {
            long userCount = userRepository.count();
            long roleCount = roleRepository.count();
            return String.format("Database connected successfully! Users: %d, Roles: %d", userCount, roleCount);
        } catch (Exception e) {
            return "Database connection failed: " + e.getMessage();
        }
    }
}
