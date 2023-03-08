package algafood.projetoteste.api.controller;

import algafood.projetoteste.domain.model.Cozinha;
import algafood.projetoteste.domain.model.Restaurante;
import algafood.projetoteste.domain.repository.CozinhaRepository;
import algafood.projetoteste.domain.repository.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static algafood.projetoteste.infraestructure.repository.spec.RestauranteSpecs.comFreteGratis;
import static algafood.projetoteste.infraestructure.repository.spec.RestauranteSpecs.comNomeSemelhante;

@RestController
@RequestMapping("/testes")
@RequiredArgsConstructor
public class TesteController {

    private final RestauranteRepository restauranteRepository;
    private final CozinhaRepository cozinhaRepository;

    @GetMapping("/restaurantes/por-cozinha-nome")
    public List<Restaurante> findByCozinhaNomeAndTaxaFreteBetween(
            String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
        return restauranteRepository
                .findRestaurantesByCozinhaNomeAndTaxaFreteBetween
                        (nome, taxaInicial, taxaFinal);
    }

    @GetMapping
    public List<?> find(String nome) {
        return restauranteRepository.restauranteNomeByCozinhaNome(nome);
    }

    @GetMapping("/cozinhas/{id}/restaurantes")
    public List<Restaurante> findBy(@PathVariable Long id) {
        return restauranteRepository.findByCozinhaId(id);
    }

    @GetMapping("/s")
    public List<String> findString() {
        return Arrays.asList("tudo bwm", "ola", "oi");
    }

    @GetMapping("/")
    public List<Restaurante> findByTaxaFreteZero(String nome) {
        return restauranteRepository
                .findAll(comNomeSemelhante(nome).and(comFreteGratis()));
    }

    @GetMapping("/f")
    public Cozinha findOrFail(Long id) {
        return cozinhaRepository.findOrFail(id);
    }

}
