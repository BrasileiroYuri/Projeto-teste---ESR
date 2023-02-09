package algafood.projetoteste.infraestructure.repository;

import algafood.projetoteste.domain.model.Cozinha;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CozinhaRepositoryImpl {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Cozinha> listar() {
        return entityManager.createQuery("from Cozinha", Cozinha.class).getResultList();
    }

    public Cozinha buscar(Long id) {
        return entityManager.find(Cozinha.class, id);
    }

    public List<Cozinha> consultarPorNome(String nome) {
        return entityManager.createQuery("FROM Cozinha WHERE nome = :nome ", Cozinha.class)
                .setParameter("nome", nome).getResultList();
    }

    public List<Cozinha> consultar(String nome) {
        return entityManager.createQuery("select c FROM Cozinha as c inner join Restaurante as r " +
                "on r.nome= :nome ", Cozinha.class).setParameter("nome", nome).getResultList();
    }

    public Cozinha salvar(Cozinha cozinha) {
        return entityManager.merge(cozinha);
    }

    public void remover(Long id) {
        var cozinha = buscar(id);
        if (cozinha == null) 
            throw new EmptyResultDataAccessException(1);
        entityManager.remove(cozinha);
    }

}
