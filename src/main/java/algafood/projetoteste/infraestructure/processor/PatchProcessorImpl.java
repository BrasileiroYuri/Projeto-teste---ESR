package algafood.projetoteste.infraestructure.processor;

import algafood.projetoteste.api.processor.PatchProcessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Map;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static org.springframework.util.ReflectionUtils.*;

@Component
public class PatchProcessorImpl implements PatchProcessor {

    @Override
    public <T> void merge(Map<String, Object> campos, T entity, HttpServletRequest request) {
        ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(FAIL_ON_IGNORED_PROPERTIES, true);
            objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, true);
            Object entityOrigem = objectMapper.convertValue(campos, entity.getClass());
            campos.forEach((key, value) -> {
                Field field = findField(entity.getClass(), key);
                field.setAccessible(true);
                Object novoValor = getField(field, entityOrigem);
                setField(field, entity, novoValor);
            });
        } catch (IllegalArgumentException ex) {
            Throwable rootCause = ExceptionUtils.getRootCause(ex);
            throw new HttpMessageNotReadableException(ex.getMessage(), rootCause, serverHttpRequest);
        }
    }

}
