package br.com.dv.antifraud.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnumValueValidator implements ConstraintValidator<EnumValue, Enum<?>> {

    private List<Enum<?>> acceptedValues;

    @Override
    public void initialize(EnumValue constraint) {
        acceptedValues = Stream.of(constraint.enumClass().getEnumConstants()).collect(Collectors.toList());
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        return value == null || acceptedValues.contains(value);
    }

}
