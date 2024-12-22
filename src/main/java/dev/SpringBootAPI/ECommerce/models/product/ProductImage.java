package dev.SpringBootAPI.ECommerce.models.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ProductImages")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "The image URL cannot be null.")
    @Size(max = 255, message = "The image URL must have a maximum of 255 characters.")
    @Column(nullable = false, length = 255)
    private String url;

    @NotNull(message = "The image type cannot be null.")
    @Size(max = 50, message = "The image type must have a maximum of 50 characters.")
    @Column(nullable = false, length = 50)
    private String type;

    @Column(nullable = false)
    @Min(value = 0)
    private Integer position;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
