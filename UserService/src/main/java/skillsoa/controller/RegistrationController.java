package skillsoa.controller;

import org.springframework.web.bind.annotation.*;

import skillsoa.model.Users;
import skillsoa.service.UsersService;

@RestController
@RequestMapping("/users")
public class RegistrationController {

    private final UsersService US;

    public RegistrationController(UsersService US) {
        this.US = US;
    }

    @PostMapping("/register")
    public Object register(@RequestBody Users U) {
        return US.registerUser(U);
    }
}
