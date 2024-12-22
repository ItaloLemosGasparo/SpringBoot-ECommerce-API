package dev.SpringBootAPI.ECommerce.dtos.user;

import dev.SpringBootAPI.ECommerce.validators.address.ValidZipcode;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AddressDTO {

    private Integer id;

    @ValidZipcode(message = "The CEP must follow the following pattern 00000-000.")
    private String zipCode;

    @Size(min = 3, max = 255, message = "The street must have between 3 and 255 characters.")
    private String street;

    @Size(min = 3, max = 100, message = "The complement must have between 3 and 100 characters.")
    private String complement;

    @Size(max = 6, message = "The number can have only 6 characters at the maximum.")
    private String number;

    @Size(min = 3, max = 255, message = "The neighborhood must have between 3 and 100 characters.")
    private String neighborhood;

    @Size(min = 3, max = 150, message = "The city must have between 3 and 100 characters.")
    private String city;

    @Pattern(regexp = "^[A-Z]{2}$", message = "The state/UF must consist of exactly two uppercase letters.")
    private String state;

    private UUID userId;
}
