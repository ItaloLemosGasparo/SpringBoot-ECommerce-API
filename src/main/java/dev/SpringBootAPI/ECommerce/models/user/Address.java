package dev.SpringBootAPI.ECommerce.models.user;

import dev.SpringBootAPI.ECommerce.validators.address.ValidZipcode;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "UserAddresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "The CEP can't be null.")
    @ValidZipcode(message = "The CEP must follow the following pattern 00000-000.")
    @Column(nullable = false, length = 9)
    private String zipCode;

    @NotNull(message = "The street can't be null.")
    @Size(min = 3, max = 255, message = "The street must have between 3 and 255 characters.")
    @Column(nullable = false, length = 255)
    private String street;

    @Size(min = 3, max = 100, message = "The complement must have between 3 and 100 characters.")
    @Column(nullable = true, length = 100)
    private String complement;

    @NotNull(message = "The number can't be null.")
    @Size(max = 6, message = "The number can have only 6 characters at the maximum.")
    @Column(nullable = false, length = 6)
    private String number;

    @NotNull(message = "The neighborhood can't be null.")
    @Size(min = 3, max = 255, message = "The neighborhood must have between 3 and 100 characters.")
    @Column(nullable = false, length = 255)
    private String neighborhood;

    @NotNull(message = "The city can't be null.")
    @Size(min = 3, max = 150, message = "The city must have between 3 and 100 characters.")
    @Column(nullable = false, length = 150)
    private String city;

    @NotNull(message = "The UF can't be null.")
    @Pattern(regexp = "^[A-Z]{2}$", message = "The state/UF must consist of exactly two uppercase letters.")

    @Column(nullable = false, length = 2)
    private String state;

    // Chave estrangeira para o id do User
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
