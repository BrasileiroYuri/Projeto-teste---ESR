package algafood.projetoteste.domain.service;

import algafood.projetoteste.domain.exception.EntidadeNaoEncontradaException;
import algafood.projetoteste.domain.model.Cozinha;
import algafood.projetoteste.domain.model.Restaurante;
import algafood.projetoteste.domain.repository.CozinhaRepository;
import algafood.projetoteste.domain.repository.RestauranteRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.validation.ConstraintViolationException;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static algafood.projetoteste.api.exceptionhandler.ProblemType.DADOS_INVALIDOS;
import static algafood.projetoteste.api.exceptionhandler.ProblemType.RECURSO_NAO_ENCONTRADO;
import static algafood.projetoteste.utils.ResourceUtils.getContentFromResource;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class RestauranteServiceIT {

    @LocalServerPort
    private int port;
    private String restauranteTailandes;

    @Autowired
    private RestauranteService restauranteService;
    @Autowired
    private RestauranteRepository restauranteRepository;
    @Autowired
    private Flyway flyway;
    @Autowired
    private CozinhaRepository cozinhaRepository;

    @BeforeEach
    void setUp() {
        RestAssured.basePath = "/restaurantes";
        RestAssured.port = this.port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        prepararJsons();

        flyway.migrate();
    }

    private void prepararJsons() {
        restauranteTailandes = getContentFromResource("/json/restaurantes/RestauranteTailandes.json");
    }

    @Test
    void deveRetornarStatus200_QuandoConsultarRestaurantes() {
        given().when().get().then().statusCode(HttpStatus.OK.value()).log().all();
    }

    @Test
    void deveRetornarStatus200ECorpoCorreto_QuandoConsultarRestauranteUnico() {
        Restaurante restaurante = restauranteService.buscarOuFalhar(1L);

        given().pathParam("id", 1).when().get("/{id}").then().statusCode(HttpStatus.OK.value())
                .body("id", equalTo(1))
                .body("nome", equalTo(restaurante.getNome()))
                .body("taxaFrete", equalTo(10.0F))
                .body("cozinha.id", equalTo(1))
        .log().all();

    }

    @Test
    void deveFalhar_QuandoConsultarRestauranteInexistente() {
        long id = getIdInexistente();

        given().pathParam("id", id).when().get("/{id}")
                .then().statusCode(HttpStatus.NOT_FOUND.value()).log().all();

    }

    @Test
    void deveRetornarStatus201_QuandoCadastrarRestauranteValido() {
        Restaurante restaurante = createRestaurante("Tailandes", 20.00, 1L);

        given().body(restaurante).contentType(ContentType.JSON)
                .when().post().then().statusCode(HttpStatus.CREATED.value()).log().all();

    }

    @Test
    void deveRetornarCorpoAdequado_QuandoCadastrarRestauranteValido() {
        Restaurante restaurante = createRestaurante("Tailandes", 20.00, 1L);

        given().body(restaurante).contentType(ContentType.JSON)
                .when().post().then().statusCode(HttpStatus.CREATED.value())
                .body("nome", equalTo(restaurante.getNome()))
                .body("cozinha.nome", equalTo("Tailandesa"))
                .body("taxaFrete", equalTo(20))
        .log().all();

    }



    @Test
    void deveCadastrar_QuandoRestauranteValido() {
        Restaurante restaurante = createRestaurante("Japones", 20.00, 1L);

        Restaurante newRestaurante = restauranteService.salvar(restaurante);

        assertThat(newRestaurante).isNotNull();
        assertThat(newRestaurante.getId()).isNotNull();
    }

    @Test
    void deveFalhar_QuandoRestauranteComCozinhaInvalida() {
        Restaurante restaurante = createRestaurante("Joanino", 12.00, null);

        given().body(restaurante).contentType(ContentType.JSON)
                .when().post().then().statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", equalTo(DADOS_INVALIDOS.getTitle()))
                .body("objects[0].name", equalTo("cozinha.id"))
        .log().all();

    }

    @Test
    void deveFalhar_QuandoRestauranteSemTaxaFrete() {
        Restaurante restaurante = createRestaurante("Japones", 10.00, 1L);
        restaurante.setTaxaFrete(null);

        given().body(restaurante).when().contentType(ContentType.JSON).post().then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", equalTo(DADOS_INVALIDOS.getTitle()))
                .body("objects[0].name", equalTo("taxaFrete"))
        .log().all();

    }

    @Test
    void deveFalhar_QuandoCadastrarRestauranteComCozinhaInexistente() {
        long cozinhaId = cozinhaRepository.count() + 1;
        Restaurante restaurante = createRestaurante("Americano", 30.00, cozinhaId);
        String detail = String.format("Cozinha de código %s não foi encontrada.", cozinhaId);

        given().body(restaurante).contentType(ContentType.JSON).when().post().then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("title", equalTo(RECURSO_NAO_ENCONTRADO.getTitle()))
                .body("detail", equalTo(detail))
        .log().all();

    }

    @Test
    void deveAtualizarComSucesso_QuandoDadosValidos() {
        Restaurante restaurante = restauranteService.buscarOuFalhar(1L);

        Restaurante novoRestaurante = createRestaurante("Americano", 20.00, 4L);

        given().body(novoRestaurante).pathParam("id", 1L).contentType(ContentType.JSON)
                .when().put("/{id}").then().statusCode(HttpStatus.OK.value())
                .body("nome", is(equalTo(novoRestaurante.getNome())))
                .body("nome", not(equalTo(restaurante.getNome())))
                .body("cozinha.id", is(equalTo(4)))
                .body("cozinha.id", not(equalTo(1)))
        .log().all();

    }

    @Test
    void deveFalhar_QuandoRestauranteInvalido() {
        Restaurante restaurante = createRestaurante(null, 30.00, 1L);

        ConstraintViolationException exception =
                assertThrows(ConstraintViolationException.class, () -> restauranteService.salvar(restaurante));

        assertThat(exception).isNotNull();
    }

    @Test
    void deveExcluir_QuandoRestauranteValido() {
        Restaurante restaurante = createRestaurante("Loanda", 30.00, 1L);
        Restaurante newRestaurante = restauranteService.salvar(restaurante);
        Long id = newRestaurante.getId();

        restauranteService.remover(id);
        EntidadeNaoEncontradaException exception =
                assertThrows(EntidadeNaoEncontradaException.class, () -> restauranteService.buscarOuFalhar(id));

        assertThat(exception).isNotNull();
    }

    @Test
    void deveFalhar_QuandoExcluirRestauranteInexistente() {
        long idInexistente = getIdInexistente();

        EntidadeNaoEncontradaException exception =
                assertThrows(EntidadeNaoEncontradaException.class, () -> restauranteService.remover(idInexistente));

        assertThat(exception).isNotNull();
    }

    private long getIdInexistente() {
        return restauranteRepository.count() + 1;
    }

    private Restaurante createRestaurante(String nome, Double value, Long cozinhaId) {
        Restaurante restaurante = new Restaurante();
        restaurante.setTaxaFrete(new BigDecimal(value));
        restaurante.setNome(nome);
        restaurante.setDataCadastro(OffsetDateTime.now());
        restaurante.setDataAtualizacao(OffsetDateTime.now());
        Cozinha cozinha = new Cozinha();
        cozinha.setId(cozinhaId);
        restaurante.setCozinha(cozinha);
        return restaurante;
    }

}
