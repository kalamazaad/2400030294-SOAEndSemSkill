package skillsoa.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import skillsoa.model.Users;
import skillsoa.repository.UserRepository;

@Service
public class UsersService {

    private final UserRepository UR;
    private final JWTService JWT;
    private final PasswordEncoder encoder;

    public UsersService(
            UserRepository UR,
            JWTService JWT,
            PasswordEncoder encoder) {

        this.UR = UR;
        this.JWT = JWT;
        this.encoder = encoder;
    }

    // Register User
    public Object registerUser(Users U) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (UR.findByUsername(U.getUsername()).isPresent()) {
                throw new RuntimeException("Username already exists");
            }

            if (UR.findByEmail(U.getEmail_id()).isPresent()) {
                throw new RuntimeException("Email already exists");
            }

            U.setPassword(encoder.encode(U.getPassword()));

            Users savedUser = UR.save(U);

            response.put("status", "success");
            response.put("message", "User registered successfully");
            response.put("user_id", savedUser.getUser_id());

        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
        }

        return response;
    }

    // Login User and Generate Token
    public Object loginUser(String username, String password) {

        Map<String, Object> response = new HashMap<>();

        try {
            Users U = UR.findByUsername(username)
                    .orElseThrow(() ->
                        new RuntimeException("Invalid username or password"));

            if (!encoder.matches(password, U.getPassword())) {
                throw new RuntimeException("Invalid username or password");
            }

            String token = JWT.generateToken(
                U.getUser_id(), U.getUsername());

            Map<String, Object> data = new HashMap<>();

            data.put("token", token);
            data.put("user_id", U.getUser_id());
            data.put("username", U.getUsername());

            response.put("status", "success");
            response.put("message", "Login successful");
            response.put("data", data);

        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
        }

        return response;
    }

    // Validate Token and Get All Users
    public Object getAllUsers(String token) {

        Map<String, Object> response = new HashMap<>();

        try {
            JWT.validateToken(token);

            List<Users> users = UR.findAll();

            response.put("status", "success");
            response.put("data", users);

        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Invalid or expired token");
        }

        return response;
    }

    // Get User by ID
    public Object getUserById(Long id, String token) {

        Map<String, Object> response = new HashMap<>();

        try {
            JWT.validateToken(token);

            Users U = UR.findById(id)
                    .orElseThrow(() ->
                        new RuntimeException("User not found"));

            response.put("status", "success");
            response.put("data", U);

        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
        }

        return response;
    }

    // Update User
    public Object updateUser(Long id, Users updatedUser, String token) {

        Map<String, Object> response = new HashMap<>();

        try {
            JWT.validateToken(token);

            Users U = UR.findById(id)
                    .orElseThrow(() ->
                        new RuntimeException("User not found"));

            U.setUsername(updatedUser.getUsername());
            U.setEmail_id(updatedUser.getEmail_id());

            if (updatedUser.getPassword() != null &&
                !updatedUser.getPassword().isBlank()) {

                U.setPassword(
                    encoder.encode(updatedUser.getPassword()));
            }

            UR.save(U);

            response.put("status", "success");
            response.put("message", "User updated successfully");

        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
        }

        return response;
    }

    // Delete User
    public Object deleteUser(Long id, String token) {

        Map<String, Object> response = new HashMap<>();

        try {
            JWT.validateToken(token);

            if (!UR.existsById(id)) {
                throw new RuntimeException("User not found");
            }

            UR.deleteById(id);

            response.put("status", "success");
            response.put("message", "User deleted successfully");

        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
        }

        return response;
    }
}
