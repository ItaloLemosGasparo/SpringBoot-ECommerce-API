package dev.SpringBootAPI.ECommerce.models.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.management.ConstructorParameters;
import java.beans.ConstructorProperties;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "UserTypes")
public class UserType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "The name can't be null.")
    @Size(min = 3, max = 100, message = "The name must have between 3 and 100 characters.")
    @Column(nullable = false, length = 100)
    private String name;

    @NotNull(message = "The description can't be null.")
    @Size(min = 3, max = 255, message = "The description must have between 3 and 255 characters.")
    @Column(nullable = false, length = 255)
    private String description;

    @OneToMany(mappedBy = "userType")
    private List<User> user;

    public UserType(Integer id) {
        this.id = id;
    }
}
