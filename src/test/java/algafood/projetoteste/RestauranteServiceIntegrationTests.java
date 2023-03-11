package algafood.projetoteste;

import algafood.projetoteste.domain.exception.EntidadeNaoEncontradaException;
import algafood.projetoteste.domain.model.Cozinha;
import algafood.projetoteste.domain.model.Restaurante;
import algafood.projetoteste.domain.repository.RestauranteRepository;
import algafood.projetoteste.domain.service.RestauranteService;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class RestauranteServiceIntegrationTests {

    @Autowired
    private RestauranteService restauranteService;
    @Autowired
    private RestauranteRepository restauranteRepository;

    @Test
    void deveCadastrar_QuandoRestauranteValido() {
        Restaurante restaurante = createRestaurante(20.00, "Japones", 1L);

        Restaurante newRestaurante = restauranteService.salvar(restaurante);

        assertThat(newRestaurante).isNotNull();
        assertThat(newRestaurante.getId()).isNotNull();
    }

    @Test
    void deveFalhar_QuandoRestauranteInvalido() {
        Restaurante restaurante = createRestaurante(30.00, null, 1L);

        ConstraintViolationException exception =
                assertThrows(ConstraintViolationException.class, () -> restauranteService.salvar(restaurante));

        assertThat(exception).isNotNull();
    }

    @Test
    void deveExcluir_QuandoRestauranteValido() {
        Restaurante restaurante = createRestaurante(30.00, "Loanda", 1L);
        Restaurante newRestaurante = restauranteService.salvar(restaurante);
        Long id = newRestaurante.getId();

        restauranteService.remover(id);

        EntidadeNaoEncontradaException exception =
                assertThrows(EntidadeNaoEncontradaException.class, () -> restauranteService.buscarOuFalhar(id));
        assertThat(exception).isNotNull();
    }

    @Test
    void deveFalhar_QuandoExcluirRestauranteInexistente() {
        Restaurante restaurante = new Restaurante();
        long idInexistente = restauranteRepository.count() + 1;

        EmptyResultDataAccessException exception =
                assertThrows(EmptyResultDataAccessException.class, () -> restauranteService.remover(idInexistente));

        assertThat(exception).isNotNull();
    }





    private Restaurante createRestaurante(double value, String nome, long cozinhaId) {
        Restaurante restaurante = new Restaurante();
        restaurante.setTaxaFrete(new BigDecimal(value));
        restaurante.setNome(nome);
        restaurante.setDataAtualizacao(LocalDateTime.now());
        restaurante.setDataCadastro(LocalDateTime.now());
        Cozinha cozinha = new Cozinha();
        cozinha.setId(cozinhaId);
        restaurante.setCozinha(cozinha);
        return restaurante;
    }

}
