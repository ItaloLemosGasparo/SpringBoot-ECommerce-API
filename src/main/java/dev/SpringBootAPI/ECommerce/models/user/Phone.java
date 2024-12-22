package dev.SpringBootAPI.ECommerce.models.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
@Table(name = "UserPhones")
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "The DDD can't be null.")
    @Pattern(regexp = "^\\d{2}$", message = "The DDD must be exactly 2 digits")
    @Column(nullable = false, length = 2)
    private String ddd;

    @NotNull(message = "The phone number can't be null.")
    @Pattern(regexp = "^\\d{8,9}$", message = "The phone number must have 8 or 9 digits")
    @Column(nullable = false, length = 9)
    private String number;

    private boolean confirmed = false;

    @NotNull(message = "The user id can't be null.")
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
