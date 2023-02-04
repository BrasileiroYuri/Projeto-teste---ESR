package algafood.projetoteste.infraestructure.repository;

import algafood.projetoteste.domain.model.Cidade;
import algafood.projetoteste.domain.repository.CidadeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CidadeRepositoryImpl implements CidadeRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Cidade> listar() {
        return entityManager.createQuery("FROM Cidade ", Cidade.class).getResultList();
    }

    @Override
    public Cidade buscar(Long id) {
        return entityManager.find(Cidade.class, id);
    }

    @Override
    public Cidade salvar(Cidade cidade) {
        return entityManager.merge(cidade);
    }

    @Override
    public void remover(Long id) {
        entityManager.remove(buscar(id));
    }

}
