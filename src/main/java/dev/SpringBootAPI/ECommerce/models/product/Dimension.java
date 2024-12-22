package dev.SpringBootAPI.ECommerce.models.product;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
@AllArgsConstructor
public class Dimension {

    @DecimalMin(value = "0.0", inclusive = false, message = "The width must be greater than 0.")
    private double width;

    @DecimalMin(value = "0.0", inclusive = false, message = "The height must be greater than 0.")
    private double height;

    @DecimalMin(value = "0.0", inclusive = false, message = "The depth must be greater than 0.")
    private double depth;
}
