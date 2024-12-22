package dev.SpringBootAPI.ECommerce.dtos.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ProductPreviewDTO {

    private UUID id;

    @Size(min = 3, max = 100, message = "The name must be between 3 and 100 characters.")
    private String name;

    @DecimalMin(value = "0.0", inclusive = false, message = "The price must be greater than 0.")
    private BigDecimal price;

    @Min(value = 0, message = "The quantity must be at least 0.")
    private Long quantity;

    @Size(min = 3, max = 100, message = "The brand must be between 3 and 100 characters.")
    private String brand;

    private List<ProductImageDTO> productImages;
}
