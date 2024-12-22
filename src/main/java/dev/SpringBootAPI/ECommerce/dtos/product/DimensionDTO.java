package dev.SpringBootAPI.ECommerce.dtos.product;

import jakarta.validation.constraints.DecimalMin;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DimensionDTO {

    @DecimalMin(value = "0.0", inclusive = false, message = "The width must be greater than 0.")
    private double width;

    @DecimalMin(value = "0.0", inclusive = false, message = "The height must be greater than 0.")
    private double height;

    @DecimalMin(value = "0.0", inclusive = false, message = "The depth must be greater than 0.")
    private double depth;

}
