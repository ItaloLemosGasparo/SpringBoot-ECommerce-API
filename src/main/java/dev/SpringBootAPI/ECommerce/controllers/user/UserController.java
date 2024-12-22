package dev.SpringBootAPI.ECommerce.controllers.user;

import dev.SpringBootAPI.ECommerce.dtos.user.UserDTO;
import dev.SpringBootAPI.ECommerce.models.user.Password;
import dev.SpringBootAPI.ECommerce.models.user.User;
import dev.SpringBootAPI.ECommerce.services.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    //Create
    @PostMapping("/register")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody User user) {
        return ResponseEntity.status(201).body(userService.createUser(user));
    }
    //

    //Read
    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> userDTOs = userService.findAll();
        return userDTOs.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(userDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable UUID id) {
        Optional<UserDTO> userDTO = userService.findById(id);
        return userDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    //

    //Update
    @PutMapping
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO updateUserDTO) {
        Optional<UserDTO> existingUserDTO = userService.findById(updateUserDTO.getId());

        return existingUserDTO.map(userDTO -> ResponseEntity.ok(userService.updateUser(userDTO.getId(), updateUserDTO)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/changePassword")
    public ResponseEntity<Void> inactiveActiveUser(@PathVariable UUID id, @Valid @RequestBody Password password) {
        Optional<UserDTO> existingUserDTO = userService.findById(id);

        if (existingUserDTO.isEmpty())
            return ResponseEntity.notFound().build();

        userService.updateUserPassword(id, password);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/inactiveActive")
    public ResponseEntity<Void> inactiveActiveUser(@PathVariable UUID id) {
        Optional<UserDTO> userDTO = userService.findById(id);

        if (userDTO.isEmpty())
            return ResponseEntity.notFound().build();

        userService.inactiveActiveUser(id);
        return ResponseEntity.ok().build();
    }
    //

    //Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        Optional<UserDTO> userDTO = userService.findById(id);

        if (userDTO.isEmpty())
            return ResponseEntity.notFound().build();

        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
    //
}
