package utils;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class DoubleListValidator implements ConstraintValidator<ValidDoubleList, List<Double>> {
    @Override
    public boolean isValid(List<Double> value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        for (Double v : value) {
            if (v == null || v < -2 || v > 2) {
                return false;
            }
        }
        return true;
    }
}
