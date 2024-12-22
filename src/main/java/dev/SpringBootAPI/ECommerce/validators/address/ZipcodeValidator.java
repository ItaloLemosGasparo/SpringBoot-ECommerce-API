package dev.SpringBootAPI.ECommerce.validators.address;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ZipcodeValidator implements ConstraintValidator<ValidZipcode, String> {

    // Padrão brasileiro "00000-000"
    private static final String ZIPCODE_REGEX = "\\d{5}-\\d{3}";

    @Override
    public boolean isValid(String zipcode, ConstraintValidatorContext context) {
        if (zipcode == null || zipcode.isEmpty()) {
            return true; // Opcional: considere CEPs nulos/vazios válidos ou não
        }
        return zipcode.matches(ZIPCODE_REGEX);
    }
}
