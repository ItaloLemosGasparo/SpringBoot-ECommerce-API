package dev.SpringBootAPI.ECommerce.controllers.user;

import dev.SpringBootAPI.ECommerce.dtos.user.UserTypeDTO;
import dev.SpringBootAPI.ECommerce.models.user.UserType;
import dev.SpringBootAPI.ECommerce.services.user.UserTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/userType")
public class UserTypeController {

    @Autowired
    UserTypeService userTypeService;

    //Create
    @PostMapping
    public ResponseEntity<UserTypeDTO> createUser(@Valid @RequestBody UserType userType) {
        return ResponseEntity.status(201).body(userTypeService.createUserType(userType));
    }
    //

    //Read
    @GetMapping
    public ResponseEntity<List<UserTypeDTO>> getUserTypes() {
        List<UserTypeDTO> userTypesDTOs = userTypeService.getUserTypes();

        return userTypesDTOs.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(userTypesDTOs);
    }
    //

    //Update
    @PutMapping
    public ResponseEntity<UserTypeDTO> updateUserType(@Valid @RequestBody UserTypeDTO updateUserTypeDTO) {
        Optional<UserTypeDTO> existingUserTypeDTO = userTypeService.getUserTypeById(updateUserTypeDTO.getId());

        return existingUserTypeDTO.map(userTypeDTO -> ResponseEntity.ok(userTypeService.updateUserType(userTypeDTO, updateUserTypeDTO)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    //

    //Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserType(@PathVariable int id) {
        Optional<UserTypeDTO> userTypeDTO = userTypeService.getUserTypeById(id);

        if (userTypeDTO.isEmpty())
            return ResponseEntity.notFound().build();

        userTypeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    //
}
