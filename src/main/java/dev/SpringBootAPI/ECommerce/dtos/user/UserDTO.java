package dev.SpringBootAPI.ECommerce.dtos.user;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class UserDTO {

    private UUID id;

    @Size(min = 3, max = 100, message = "The name must have between 3 to 100 characters.")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s]+$", message = "The name can only contain letters and spaces.")
    private String name;

    @Email(message = "Invalid email.")
    @Size(max = 100, message = "The email can have a maximum of 100 characters.")
    private String email;

    @Past(message = "The birth date must be in the past.")
    private LocalDate birthDate;

    private Boolean active;

    private String url;

    @PastOrPresent(message = "The 'created at' date must be in the past or present.")
    private LocalDate createdAt;

    @PastOrPresent(message = "The 'updated at' date must be in the past or present.")
    private LocalDate updatedAt;

    private Integer userType;
}
