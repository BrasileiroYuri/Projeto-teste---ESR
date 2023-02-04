package algafood.projetoteste.infraestructure.repository;

import algafood.projetoteste.domain.model.Cozinha;
import algafood.projetoteste.domain.repository.CozinhaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CozinhaRepositoryImpl implements CozinhaRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Cozinha> listar() {
		return entityManager.createQuery("from Cozinha", Cozinha.class).getResultList();
	}

	@Override
	public Cozinha buscar(Long id) {
			return entityManager.find(Cozinha.class, id);
	}

	@Override
	public Cozinha salvar(Cozinha cozinha) {
		return entityManager.merge(cozinha);
	}

	@Override
	public void remover(Long id) {
		entityManager.remove(buscar(id));
	}
}
