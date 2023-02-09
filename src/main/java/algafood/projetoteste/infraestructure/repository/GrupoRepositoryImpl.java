package algafood.projetoteste.infraestructure.repository;

import algafood.projetoteste.domain.model.Grupo;
import algafood.projetoteste.domain.repository.GrupoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GrupoRepositoryImpl implements GrupoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Grupo> listar() {
        return entityManager.createQuery("from Grupo", Grupo.class).getResultList();
    }

    @Override
    public Grupo buscar(Long id) {
        return entityManager.find(Grupo.class, id);
    }

    @Override
    public Grupo salvar(Grupo grupo) {
        return entityManager.merge(grupo);
    }

    @Override
    public void remover(Long id) {
        entityManager.remove(buscar(id));
    }
}
