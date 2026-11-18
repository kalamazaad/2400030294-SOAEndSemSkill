package skillsoa.controller;

import org.springframework.web.bind.annotation.*;

import skillsoa.model.Users;
import skillsoa.service.UsersService;

@RestController
@RequestMapping("/users")
public class AuthenticationController {

    private final UsersService US;

    public AuthenticationController(UsersService US) {
        this.US = US;
    }

    // Login
    @PostMapping("/login")
    public Object login(@RequestBody Users U) {
        return US.loginUser(
            U.getUsername(), U.getPassword());
    }

    // Get all users
    @GetMapping
    public Object getAllUsers(
            @RequestHeader("token") String token) {

        return US.getAllUsers(token);
    }

    // Get user by ID
    @GetMapping("/{id}")
    public Object getUserById(
            @PathVariable Long id,
            @RequestHeader("token") String token) {

        return US.getUserById(id, token);
    }

    // Update user
    @PutMapping("/{id}")
    public Object updateUser(
            @PathVariable Long id,
            @RequestBody Users U,
            @RequestHeader("token") String token) {

        return US.updateUser(id, U, token);
    }

    // Delete user
    @DeleteMapping("/{id}")
    public Object deleteUser(
            @PathVariable Long id,
            @RequestHeader("token") String token) {

        return US.deleteUser(id, token);
    }
}
