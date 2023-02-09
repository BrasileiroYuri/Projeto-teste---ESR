package algafood.projetoteste.infraestructure.repository;

import algafood.projetoteste.domain.model.Estado;
import algafood.projetoteste.domain.repository.EstadoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
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

    @Transactional
    @Override
    public Estado salvar(Estado estado) {
        return entityManager.merge(estado);
    }

    @Transactional
    @Override
    public void remover(Long id) {
        Estado estado = buscar(id);
        if (estado == null)
            throw new EmptyResultDataAccessException(1);
        entityManager.remove(estado);
    }

}
