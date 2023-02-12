package algafood.projetoteste.domain.repository;

import algafood.projetoteste.domain.model.Restaurante;

import java.math.BigDecimal;
import java.util.List;

public interface RestauranteRepositoryQueries {

    List<Restaurante> findB
            (String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);

}
