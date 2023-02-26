package algafood.projetoteste.domain.repository;

import algafood.projetoteste.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long>,
        RestauranteRepositoryQueries, JpaSpecificationExecutor<Restaurante> {

    @Override
    @Query("FROM Restaurante r JOIN FETCH r.cozinha")
//            " LEFT JOIN FETCH r.formasPagamento")
    List<Restaurante> findAll();

    Restaurante consultarPorNome(String nome);

    List<Restaurante> findByCozinhaId(Long id);

    Restaurante findByNomeAndTaxaFreteBetween(
            String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);

    List<Restaurante> findByCozinhaNomeContainingAndTaxaFreteBetween(
            String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);

}
