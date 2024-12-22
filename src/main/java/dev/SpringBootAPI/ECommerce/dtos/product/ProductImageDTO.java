package dev.SpringBootAPI.ECommerce.dtos.product;

import dev.SpringBootAPI.ECommerce.models.product.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductImageDTO {

    private int id;

    @Size(max = 255, message = "The image URL must have a maximum of 255 characters.")
    private String url;

    @Size(max = 50, message = "The image type must have a maximum of 50 characters.")
    private String type;

    @Min(value = 0)
    private Integer position;

    private Product product;
}
