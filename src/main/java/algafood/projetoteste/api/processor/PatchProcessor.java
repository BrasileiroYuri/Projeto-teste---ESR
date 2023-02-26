package algafood.projetoteste.api.processor;

import java.util.Map;

public interface PatchProcessor {

    <T> void merge(Map<String, Object> campos, T entity);

}
