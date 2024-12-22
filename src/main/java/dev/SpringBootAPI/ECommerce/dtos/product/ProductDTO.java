package dev.SpringBootAPI.ECommerce.dtos.product;

import dev.SpringBootAPI.ECommerce.dtos.user.UserDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {

    private UUID id;

    @Size(min = 3, max = 100, message = "The name must be between 3 and 100 characters.")
    private String name;

    @Size(min = 100, max = 2000, message = "The description must be between 100 and 2000 characters.")
    private String description;

    @DecimalMin(value = "0.0", inclusive = false, message = "The price must be greater than 0.")
    private BigDecimal price;

    @Min(value = 0, message = "The quantity must be at least 0.")
    private Long quantity;

    private CategoryDTO category;

    private UUID seller;

    @Size(min = 3, max = 100, message = "The brand must be between 3 and 100 characters.")
    private String brand;

    @PastOrPresent(message = "The creation date must be in the past or present.")
    private LocalDateTime createdAt;

    @PastOrPresent(message = "The update date must be in the past or present.")
    private LocalDateTime updatedAt;

    private boolean status;

    @DecimalMin(value = "0.0", inclusive = true, message = "The weight cannot be less than 0.")
    private BigDecimal weight;

    @Embedded
    private DimensionDTO dimension;

    private List<ProductImageDTO> productImages;
}
