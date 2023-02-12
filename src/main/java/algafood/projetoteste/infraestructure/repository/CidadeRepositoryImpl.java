package algafood.projetoteste.infraestructure.repository;

import algafood.projetoteste.domain.model.Cidade;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CidadeRepositoryImpl {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Cidade> findByNomeContaining(String nome) {
        var jpql = "from Cidade where nome like:nome ";
        return entityManager.createQuery(jpql, Cidade.class).getResultList();
    }

}