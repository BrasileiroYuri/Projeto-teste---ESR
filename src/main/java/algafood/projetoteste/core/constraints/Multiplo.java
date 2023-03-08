package algafood.projetoteste.core.constraints;

import algafood.projetoteste.core.validation.MultiploValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {MultiploValidator.class})
public @interface Multiplo {

    int numero();

    String message() default "{multiplo.invalido}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
