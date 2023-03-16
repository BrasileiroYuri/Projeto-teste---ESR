package algafood.projetoteste.infraestructure.processor;

import algafood.projetoteste.api.processor.MergeProcessor;
import algafood.projetoteste.domain.exception.ValidacaoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;

@Component
@RequiredArgsConstructor
public class MergeProcessorImpl implements MergeProcessor {

    private final SmartValidator smartValidator;

    @Override
    public <T> void validate(T entity) {
        String simpleName = entity.getClass().getSimpleName();
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(entity, simpleName);
        smartValidator.validate(entity, bindingResult);
        if (bindingResult.hasErrors()) throw new ValidacaoException(bindingResult);
    }

}
