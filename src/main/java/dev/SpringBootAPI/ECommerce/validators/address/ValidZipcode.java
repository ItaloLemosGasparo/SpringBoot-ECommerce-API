package dev.SpringBootAPI.ECommerce.validators.address;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ZipcodeValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidZipcode {
    String message() default "CEP inválido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}