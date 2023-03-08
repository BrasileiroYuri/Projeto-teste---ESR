package algafood.projetoteste.core.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.OverridesAttribute;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@NotNull
@Min(1)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
public @interface IdValidation {

    @OverridesAttribute(constraint = NotNull.class, name = "message")
    @OverridesAttribute(constraint = Min.class, name = "message")
    String message() default "{0} Não deve ser nula nem menor que 1.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}