package algafood.projetoteste.domain.repository;

import algafood.projetoteste.domain.model.Estado;

import java.util.List;

public interface EstadoRepository {

    List<Estado> listar();

    Estado buscar(Long id);

    Estado salvar(Estado estado);

    void remover(Long id);

}
