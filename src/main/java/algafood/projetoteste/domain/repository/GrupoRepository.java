package algafood.projetoteste.domain.repository;

import algafood.projetoteste.domain.model.Grupo;

import java.util.List;

public interface GrupoRepository {

    List<Grupo> listar();

    Grupo buscar(Long id);

    Grupo salvar(Grupo grupo);

    void remover(Long id);

}
