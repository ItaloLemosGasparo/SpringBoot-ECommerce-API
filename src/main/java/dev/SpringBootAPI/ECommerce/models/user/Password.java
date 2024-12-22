package dev.SpringBootAPI.ECommerce.models.user;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class Password {
    @NotNull(message = "The password can't be null")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}", message = "The password must have at least 8 characters and include letters, numbers, and special characters.")
    @Column(nullable = false)
    private String password;
}
