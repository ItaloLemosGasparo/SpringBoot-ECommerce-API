package dev.SpringBootAPI.ECommerce.controllers.user;

import dev.SpringBootAPI.ECommerce.dtos.user.PhoneDTO;
import dev.SpringBootAPI.ECommerce.models.user.Phone;
import dev.SpringBootAPI.ECommerce.services.user.PhoneService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/user/{userId}/phone")
public class PhoneController {

    @Autowired
    private PhoneService phoneService;

    //Create
    @PostMapping
    public ResponseEntity<PhoneDTO> createPhone(@Valid @RequestBody Phone Phone) {
        return ResponseEntity.ok(phoneService.createPhone(Phone));
    }
    //

    //Read
    @GetMapping
    public ResponseEntity<List<PhoneDTO>> getPhones(@PathVariable UUID userId) {
        List<PhoneDTO> phoneDTOs = phoneService.findAllByUserId(userId);

        return phoneDTOs.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(phoneDTOs);
    }
    //

    //Update
    @PutMapping("/{phoneId}")
    public ResponseEntity<PhoneDTO> updatePhone(@PathVariable UUID userId, @PathVariable Long phoneId, @Valid @RequestBody PhoneDTO updatePhone) {
        return phoneService.findById(phoneId)
                .map(phoneDTO -> ResponseEntity.ok(phoneService.updatePhone(phoneDTO.getId(), updatePhone)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/confirm/{phoneId}")
    public ResponseEntity<PhoneDTO> confirmPhone(@PathVariable UUID userId, @PathVariable Long phoneId) {
        return phoneService.findById(phoneId)
                .map(phoneDTO -> ResponseEntity.ok(phoneService.confirmPhone(phoneDTO.getId())))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    //

    //Delete
    @DeleteMapping("/{phoneId}")
    public ResponseEntity<Void> deletePhone(@PathVariable UUID userId, @PathVariable Long phoneId) {
        Optional<PhoneDTO> phoneDTO = phoneService.findById(phoneId);

        if (phoneDTO.isEmpty())
            return ResponseEntity.notFound().build();

        phoneService.deletePhone(phoneDTO.get().getId());
        return ResponseEntity.ok().build();
    }
    //
}
