package br.com.dv.antifraud.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CardNumberValidator implements ConstraintValidator<CardNumber, String> {

    @Override
    public boolean isValid(String cardNumber, ConstraintValidatorContext context) {
        if (isCardNumberNullOrBlank(cardNumber)) {
            return false;
        }

        return validateLuhn(cardNumber);
    }

    private boolean isCardNumberNullOrBlank(String cardNumber) {
        return cardNumber == null || cardNumber.isBlank();
    }

    private boolean validateLuhn(String cardNumber) {
        int totalDigits = cardNumber.length();
        int sum = 0;
        boolean alternate = false;

        for (int i = totalDigits - 1; i >= 0; --i) {
            char c = cardNumber.charAt(i);

            int n;
            try {
                n = Integer.parseInt(String.valueOf(c));
            } catch (NumberFormatException e) {
                return false;
            }

            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }

            sum += n;
            alternate = !alternate;
        }

        return (sum % 10 == 0);
    }

}
