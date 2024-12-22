package dev.SpringBootAPI.ECommerce.validators.cpf;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CpfValidator implements ConstraintValidator<ValidCpf, String> {

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        if (cpf == null || cpf.length() != 11 || !cpf.matches("\\d{11}")) {
            return false;
        }
        return isValidVerifier(cpf, 9) && isValidVerifier(cpf, 10);
    }

    private boolean isValidVerifier(String cpf, int position) {
        int sum = 0;
        for (int i = 0; i < position; i++) {
            sum += (cpf.charAt(i) - '0') * ((position + 1) - i);
        }
        int verifier = 11 - (sum % 11);
        verifier = (verifier == 10 || verifier == 11) ? 0 : verifier;
        return verifier == (cpf.charAt(position) - '0');
    }

}
