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
    String message() default "Deve ser maior que 0 e não deve ser nula";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}