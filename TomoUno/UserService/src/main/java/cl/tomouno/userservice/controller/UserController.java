package cl.tomouno.userservice.controller;

import cl.tomouno.userservice.controller.response.MessageResponse;
import cl.tomouno.userservice.model.User;
import cl.tomouno.userservice.repository.UserRepository;
import cl.tomouno.userservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }




    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    // Crud Usuario

    // Create

    @PostMapping("/")
    public ResponseEntity<MessageResponse> createUser(@RequestBody User user) {
        // revisa si el email ya esta en uso
        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Email already exists"));
        }
        // Revisa si el username ya está en uso
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Username already exists"));
        }

        userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("User created successfully"));
    }

    // Read

    @GetMapping("/")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable UUID id) {
        User user = userService.getUserById(id);
        if (user == null) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("User not found"));
        }
        return user;
    }

    // Update

    @PutMapping("/")
    public ResponseEntity<MessageResponse> updateUser(@RequestBody User user) {
        User currentUser = userService.getUserById(user.getId());
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("User not found"));
        }
        userService.deleteUser(currentUser.getId());
        userService.createUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("User updated successfully"));
    }

    // Delete

    @DeleteMapping("/")
    public ResponseEntity<MessageResponse> deleteUser(@RequestParam UUID id) {
        User currentUser = userService.getUserById(id);
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("User not found"));
        }
        userService.deleteUser(currentUser.getId());
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("User deleted successfully"));
    }


}
