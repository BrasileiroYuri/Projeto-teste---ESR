package algafood.projetoteste.infraestructure.repository;

import algafood.projetoteste.domain.model.Cidade;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CidadeRepositoryImpl {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Cidade> listar() {
        return entityManager.createQuery("FROM Cidade ", Cidade.class).getResultList();
    }

    public Cidade buscar(Long id) {
        return entityManager.find(Cidade.class, id);
    }

    public Cidade salvar(Cidade cidade) {
        return entityManager.merge(cidade);
    }

    public void remover(Long id) {
        Cidade cidade = buscar(id);
        if (cidade == null)
            throw new EmptyResultDataAccessException(1);
        entityManager.remove(cidade);
    }

}
