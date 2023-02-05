package algafood.projetoteste.domain.repository;

import algafood.projetoteste.domain.model.Restaurante;

import java.util.List;

public interface RestauranteRepository {

    List<Restaurante> listar();

    Restaurante buscar(Long id);

    Restaurante salvar(Restaurante restaurante);

    void remover  (Long id);


}
