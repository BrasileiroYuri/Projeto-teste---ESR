package algafood.projetoteste.infraestructure.repository;

import algafood.projetoteste.domain.model.Estado;
import algafood.projetoteste.domain.repository.EstadoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EstadoRepositoryImpl implements EstadoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Estado> listar() {
        return entityManager.createQuery("from Estado", Estado.class).getResultList();
    }

    @Override
    public Estado buscar(Long id) {
        return entityManager.find(Estado.class, id);
    }

    @Override
    public Estado salvar(Estado estado) {
        return entityManager.merge(estado);
    }

    @Override
    public void remover(Long id) {
        entityManager.remove(buscar(id));
    }

}
