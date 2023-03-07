package algafood.projetoteste.api.processor;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public interface PatchProcessor {

    <T> void merge(Map<String, Object> campos, T entity, HttpServletRequest request);

}
