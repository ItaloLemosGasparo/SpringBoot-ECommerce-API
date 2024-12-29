package dev.SpringBootAPI.ECommerce.controllers.user;

import dev.SpringBootAPI.ECommerce.dtos.user.AddressDTO;
import dev.SpringBootAPI.ECommerce.models.user.Address;
import dev.SpringBootAPI.ECommerce.services.user.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/user/{userId}/address")
public class AddressController {

    @Autowired
    AddressService addressService;

    //Create
    @PostMapping
    public ResponseEntity<AddressDTO> createAddress(@PathVariable UUID userId, @Valid @RequestBody Address address) {
        return ResponseEntity.ok(addressService.createAddress(userId, address));
    }
    //

    //Read
    @GetMapping
    public ResponseEntity<List<AddressDTO>> getAddressesByUserId(@PathVariable UUID userId) {
        List<AddressDTO> addressesDTOs = addressService.getAddressesByUserId(userId);
        return addressesDTOs.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(addressesDTOs);
    }
    //

    //Update
    @PutMapping("/{addressId}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable Integer addressId, @Valid @RequestBody AddressDTO updateAddressDTO) {
        return addressService.getAddressById(addressId)
                .map(addressDTO -> ResponseEntity.ok(addressService.updateAddress(updateAddressDTO)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    //

    //Delete
    @DeleteMapping
    public ResponseEntity<Void> deleteAddress(@PathVariable Integer id) {
        Optional<AddressDTO> addressDTO = addressService.getAddressById(id);

        if (addressDTO.isEmpty())
            return ResponseEntity.notFound().build();

        addressService.deleteBYId(id);
        return ResponseEntity.noContent().build();
    }
    //
}
