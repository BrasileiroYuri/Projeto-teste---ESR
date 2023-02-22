package algafood.projetoteste.domain.repository;

import algafood.projetoteste.domain.model.Restaurante;

import java.math.BigDecimal;
import java.util.List;

public interface RestauranteRepositoryQueries {

    List<Restaurante> findRestaurantesByNomeAndTaxaFreteBetween
            (String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);

    List<Restaurante> findRestaurantesByCozinhaNomeAndTaxaFreteBetween(
            String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);

    List<?> find(String nome);

    List<String> byCozinhaNome(String nome);

    List<Restaurante> findByNomeAndFreeShipping(String nome);

}
