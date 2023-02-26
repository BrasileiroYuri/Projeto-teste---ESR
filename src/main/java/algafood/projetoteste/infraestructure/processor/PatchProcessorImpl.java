package algafood.projetoteste.infraestructure.processor;

import algafood.projetoteste.api.processor.PatchProcessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Map;

import static org.springframework.util.ReflectionUtils.*;

@Component
public class PatchProcessorImpl implements PatchProcessor {

    @Override
    public <T> void merge(Map<String, Object> campos, T entity) {
        ObjectMapper objectMapper = new ObjectMapper();
        Object entityOrigem = objectMapper.convertValue(campos, entity.getClass());
        campos.forEach((key, value) -> {
            Field field = findField(entity.getClass(), key);
            field.setAccessible(true);
            Object novoValor = getField(field, entityOrigem);
            setField(field, entity, novoValor);
        });
    }

}
