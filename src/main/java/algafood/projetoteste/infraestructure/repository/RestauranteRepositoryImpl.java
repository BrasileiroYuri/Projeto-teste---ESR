package algafood.projetoteste.infraestructure.repository;

import algafood.projetoteste.domain.model.Restaurante;
import algafood.projetoteste.domain.repository.RestauranteRepositoryQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Restaurante> findB(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
        var parametros = new HashMap<String, Object>();
        var jpql = getJpql(nome, parametros, taxaFreteFinal, taxaFreteInicial);
        TypedQuery<Restaurante> typedQuery = entityManager.createQuery(jpql.toString(), Restaurante.class);
        parametros.forEach(typedQuery::setParameter);
        return typedQuery.getResultList();
    }

    private static StringBuilder getJpql(String nome, HashMap<String, Object> paramentos,
                                         BigDecimal taxaFreteFinal, BigDecimal taxaFreteInicial) {
        var jpql = new StringBuilder();
        jpql.append("from Restaurante where 0 = 0 ");
        if (StringUtils.hasLength(nome)) {
            jpql.append("and nome like :nome ");
            paramentos.put("nome", "%" + nome + "%");
        }
        if (taxaFreteInicial != null) {
            jpql.append("and taxaFrete >= :taxaInicial ");
            paramentos.put("taxaInicial", taxaFreteInicial);
        }
        if (taxaFreteFinal != null) {
            jpql.append("and taxaFrete <= :taxaFinal");
            paramentos.put("taxaFinal", taxaFreteFinal);
        }
        return jpql;
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
