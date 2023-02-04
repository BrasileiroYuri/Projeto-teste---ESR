package algafood.projetoteste.infraestructure.repository;

import algafood.projetoteste.domain.model.Permissao;
import algafood.projetoteste.domain.repository.PermissaoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PermissaoRepositoryImpl implements PermissaoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Permissao> listar() {
        return entityManager.createQuery("from Permissao", Permissao.class).getResultList();
    }

    @Override
    public Permissao buscar(Long id) {
        return entityManager.find(Permissao.class, id);
    }

    @Override
    public Permissao salvar(Permissao permissao) {
        return entityManager.merge(permissao);
    }

    @Override
    public void remover(Long id) {
        entityManager.remove(buscar(id));
    }

}
