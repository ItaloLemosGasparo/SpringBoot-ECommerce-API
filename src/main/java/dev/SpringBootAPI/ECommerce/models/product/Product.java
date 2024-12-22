package dev.SpringBootAPI.ECommerce.models.product;

import dev.SpringBootAPI.ECommerce.models.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "Products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull(message = "The name cannot be null.")
    @Size(min = 3, max = 100, message = "The name must be between 3 and 100 characters.")
    @Column(nullable = false, length = 100)
    private String name;

    @NotNull(message = "The description cannot be null.")
    @Size(min = 100, max = 2000, message = "The description must be between 100 and 2000 characters.")
    @Column(nullable = false, length = 2000)
    private String description;

    @NotNull(message = "The price cannot be null.")
    @DecimalMin(value = "0.0", inclusive = false, message = "The price must be greater than 0.")
    @Column(nullable = false)
    private BigDecimal price;

    @NotNull(message = "The quantity cannot be null.")
    @Min(value = 0, message = "The quantity must be at least 0.")
    @Column(nullable = false)
    private Long quantity;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    private UUID seller;

    @NotNull(message = "The brand cannot be null.")
    @Size(min = 3, max = 100, message = "The brand must be between 3 and 100 characters.")
    @Column(nullable = false, length = 100)
    private String brand;

    @NotNull(message = "The creation date cannot be null.")
    @PastOrPresent(message = "The creation date must be in the past or present.")
    private LocalDateTime createdAt;

    @PastOrPresent(message = "The update date must be in the past or present.")
    private LocalDateTime updatedAt;

    private boolean status;

    @NotNull(message = "The weight cannot be null.")
    @DecimalMin(value = "0.0", inclusive = true, message = "The weight cannot be less than 0.")
    @Column(nullable = false)
    private BigDecimal weight;

    @Embedded
    private Dimension dimension;

    @OneToMany(mappedBy = "product")
    private List<ProductImage> productImages;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}