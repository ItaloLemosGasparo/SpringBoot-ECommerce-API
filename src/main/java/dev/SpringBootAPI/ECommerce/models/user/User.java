package dev.SpringBootAPI.ECommerce.models.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.SpringBootAPI.ECommerce.models.product.Product;
import dev.SpringBootAPI.ECommerce.validators.cpf.ValidCpf;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "password")
@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "UUID", updatable = false, nullable = false)
    private UUID id;

    @NotNull(message = "The name can't be null.")
    @Size(min = 3, max = 100, message = "The name must have between 3 to 100 characters.")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s]+$", message = "The name can only contain letters and spaces.")
    @Column(nullable = false, length = 100)
    private String name;

    @NotNull(message = "The email can't be null.")
    @Email(message = "Invalid email.")
    @Size(max = 100, message = "The email can have a maximum of 100 characters.")
    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @NotNull(message = "The password can't be null.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}", message = "The password must have at least 8 characters and include letters, numbers, and special characters.")
    @Column(nullable = false)
    private String password;

    @NotNull(message = "The CPF can't be null.")
    @ValidCpf(message = "Invalid CPF.")
    @Column(unique = true, nullable = false)
    private String cpf;

    @NotNull(message = "The birth date can't be null.")
    @Past(message = "The birth date must be in the past.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    // Definindo a chave estrangeira para o UserType
    @NotNull(message = "UserType can't be null.")
    @ManyToOne
    @JoinColumn(name = "userType_id", nullable = false)
    private UserType userType;

    private Boolean active = true;

    @PastOrPresent(message = "The 'created at' date must be in the past or present.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdAt;

    @PastOrPresent(message = "The 'updated at' date must be in the past or present.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate updatedAt;

    //Referencias de outras tabelas para User
    @OneToMany(mappedBy = "user")
    private List<Address> address;

    @OneToMany(mappedBy = "user")
    private List<Phone> phone;
    //

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDate.now();
    }

    @PreUpdate // Metodo executado antes de atualizar o usuário no banco de dados
    public void preUpdate() {
        this.updatedAt = LocalDate.now();
    }
}
