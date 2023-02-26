package algafood.projetoteste.infraestructure.repository;

import algafood.projetoteste.domain.exception.EntidadeNaoEncontradaException;
import algafood.projetoteste.domain.repository.CustomJpaRepository;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.util.Optional;

public class CustomJpaRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID>
        implements CustomJpaRepository<T, ID> {

    private final EntityManager entityManager;

    public CustomJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public Optional<T> findFirst(String nome) {
        String query = String.format("from %s", getDomainClass().getName());
        T singleResult = entityManager.createQuery(
                query, getDomainClass()).setMaxResults(1).getSingleResult();
        return Optional.ofNullable(singleResult);
    }

    @Override
    public T findOrFail(ID id) {
        String message = String.format("No %s for id %d",
                getDomainClass().getSimpleName(), id);
        return findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(message));
    }

}
