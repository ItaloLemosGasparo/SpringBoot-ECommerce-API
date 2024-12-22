package dev.SpringBootAPI.ECommerce.models.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "The name can't be null.")
    @Size(min = 3, max = 100, message = "The name must have between 3 and 100 characters.")
    @Column(nullable = false, length = 100)
    private String name;

    @Size(max = 255, message = "The \"iconUrl\" can have 255 characters at maximum.")
    @Column(length = 255)
    private String iconUrl;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    private Category parentCategory;

}
