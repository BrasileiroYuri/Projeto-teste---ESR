package algafood.projetoteste.infraestructure.repository;

import algafood.projetoteste.domain.model.Restaurante;
import algafood.projetoteste.domain.repository.RestauranteRepositoryQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Restaurante> findRestaurantesByNomeAndTaxaFreteBetween(
            String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Restaurante> criteriaQuery = criteriaBuilder.createQuery(Restaurante.class);
        Root<Restaurante> root = criteriaQuery.from(Restaurante.class);
        var predicates = new ArrayList<Predicate>();
        if (StringUtils.hasText(nome)) {
            predicates.add(criteriaBuilder.like(root.get("nome"), "%" + nome + "%"));
        }
        if (taxaFreteInicial != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial));
        }
        if (taxaFreteFinal != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal));
        }
        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<Restaurante> findRestaurantesByCozinhaNomeAndTaxaFreteBetween(
            String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Restaurante> criteriaQuery = criteriaBuilder.createQuery(Restaurante.class);
        Root<Restaurante> restauranteRoot = criteriaQuery.from(Restaurante.class);
        Join<Object, Object> cozinhaRoot = restauranteRoot.join("cozinha");
        Predicate nomePredicate = criteriaBuilder.like(cozinhaRoot.get("nome"), "%" + nome + "%");
        Predicate taxaInicialPredicate = criteriaBuilder
                .greaterThanOrEqualTo(restauranteRoot.get("taxaFrete"), taxaFreteInicial);
        Predicate taxaFinalPredicate = criteriaBuilder
                .lessThanOrEqualTo(restauranteRoot.get("taxaFrete"), taxaFreteFinal);
        criteriaQuery.where(nomePredicate, taxaInicialPredicate, taxaFinalPredicate);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<?> find(String nome) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<?> criteriaQuery = criteriaBuilder.createQuery();
        Root<Restaurante> restauranteRoot = criteriaQuery.from(Restaurante.class);
        Join<Object, Object> cozinhaRoot = restauranteRoot.join("cozinha");
        Predicate restauranteNome = criteriaBuilder.like(restauranteRoot.get("nome"), "%" + nome + "%");
        Predicate cozinhaNome = criteriaBuilder.like(cozinhaRoot.get("nome"), "%" + nome + "%");
        criteriaQuery.where(restauranteNome, cozinhaNome);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<String> byCozinhaNome(String nome) {
        return entityManager.createQuery("select nome from Restaurante where cozinha.nome like :nome",
                        String.class).setParameter("nome", "%"+ nome + "%").getResultList();
    }

    public List<Restaurante> listar() {
        return entityManager.createQuery("FROM Restaurante", Restaurante.class).getResultList();
    }

    public Restaurante buscar(Long id) {
        return entityManager.find(Restaurante.class, id);
    }

    @Transactional
    public Restaurante salvar(Restaurante restaurante) {
        return entityManager.merge(restaurante);
    }

    @Transactional
    public void remover(Long id) {
        Restaurante restaurante = buscar(id);
        if (restaurante == null)
            throw new EmptyResultDataAccessException(1);
        entityManager.remove(restaurante);
    }

}
