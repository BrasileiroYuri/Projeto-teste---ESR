package algafood.projetoteste.core.validation;

import algafood.projetoteste.core.constraints.ValorZeroIncluiDescricao;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ValidationException;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

public class ValorZeroIncluiDescricaoValidator implements ConstraintValidator<ValorZeroIncluiDescricao, Object> {

    private String descricaoField;
    private String valorField;
    private String descricaoObrigatoria;

    @Override
    public void initialize(ValorZeroIncluiDescricao constraintAnnotation) {
        this.descricaoField = constraintAnnotation.descricaoField();
        this.valorField = constraintAnnotation.valorField();
        this.descricaoObrigatoria = constraintAnnotation.descricaoObrigatoria();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        boolean valid = true;

        try {
            var valor = (BigDecimal) BeanUtils
                    .getPropertyDescriptor(value.getClass(), valorField).getReadMethod().invoke(value);
            var propriedade = (String) BeanUtils
                    .getPropertyDescriptor(value.getClass(), descricaoField).getReadMethod().invoke(value);

            if (valor != null && BigDecimal.ZERO.compareTo(valor) == 0 && propriedade != null) {
                descricaoObrigatoria = descricaoObrigatoria.toLowerCase();
                propriedade = propriedade.toLowerCase();
                valid = propriedade.contains(descricaoObrigatoria);
            }

            return valid;
        } catch (Exception e) {
            throw new ValidationException(e);
        }
    }

}
