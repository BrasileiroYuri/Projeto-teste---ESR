package algafood.projetoteste.domain.repository;

import algafood.projetoteste.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long>, RestauranteRepositoryQueries,
        JpaSpecificationExecutor<Restaurante> {
        Restaurante consultarPorNome(String nome);

    /*  Restaurante findByNomeAndTaxaFreteBetween
                (String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal); */

        List<Restaurante> findByCozinhaNomeContainingAndTaxaFreteBetween
        (String nome,BigDecimal taxaFreteInicial,BigDecimal taxaFreteFinal);
        }
