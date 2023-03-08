package algafood.projetoteste.core.validation;

import algafood.projetoteste.core.constraints.Multiplo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class MultiploValidator implements ConstraintValidator<Multiplo, Number> {

    private int number;

    @Override
    public void initialize(Multiplo constraintAnnotation) {
        this.number = constraintAnnotation.numero();
    }

    @Override
    public boolean isValid(Number value, ConstraintValidatorContext context) {
        boolean valido = true;

        if (value != null) {
            var valorDecimal = BigDecimal.valueOf(value.doubleValue());
            var multiploDecimal = BigDecimal.valueOf(this.number);
            var resto = valorDecimal.remainder(multiploDecimal);

            valido = BigDecimal.ZERO.compareTo(resto) == 0;
        }

        return valido;
    }

}
