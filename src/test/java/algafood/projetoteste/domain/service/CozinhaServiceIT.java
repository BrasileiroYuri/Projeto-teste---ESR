package algafood.projetoteste.domain.service;

import algafood.projetoteste.domain.exception.EntidadeEmUsoException;
import algafood.projetoteste.domain.exception.EntidadeNaoEncontradaException;
import algafood.projetoteste.domain.model.Cozinha;
import algafood.projetoteste.domain.repository.CozinhaRepository;
import algafood.projetoteste.utils.DatabaseCleaner;
import algafood.projetoteste.utils.ResourceUtils;
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

import static algafood.projetoteste.api.exceptionhandler.ProblemType.RECURSO_NAO_ENCONTRADO;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CozinhaServiceIT {

    @LocalServerPort
    private int port;
    private int cozinhasExistentes;
    private String cozinhaChinesa;

    @Autowired
    private CozinhaService cozinhaService;
    @Autowired
    private CozinhaRepository cozinhaRepository;
    @Autowired
    private DatabaseCleaner databaseCleaner;
    @Autowired
    private Flyway flyway;

    @BeforeEach
    void setUp() {
      flyway.migrate();
        RestAssured.basePath = "/cozinhas";
        RestAssured.port = this.port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        prepararDados();
//        databaseCleaner.clearTables();
//        prepararJsons();
    }

    private void prepararJsons() {
        cozinhaChinesa = ResourceUtils.getContentFromResource("/json/cozinhas/CozinhaChinesa.json");
    }

    @Test
    void deveRetornarStatus200_QuandoConsultarCozinha() {
        given().when().get().then().statusCode(HttpStatus.OK.value()).log().all();
    }

    @Test
    void deveRetornarStatus400_QuandoCozinhaInvalida() {
    given().contentType(ContentType.JSON).body(new Cozinha())
            .when().post().then().statusCode(HttpStatus.BAD_REQUEST.value()).log().all();
    }

    @Test
    void deveRetornarStatus201_QuandoPostComCozinhaValida() {
        Cozinha cozinha = createCozinha("Japonesa");

        given().contentType(ContentType.JSON).body(cozinha).when()
                .post().then().statusCode(HttpStatus.CREATED.value())
                .body("nome", equalTo(cozinha.getNome())).log().all();
    }

    @Test
    void deveRetornarStatus200_QuandoConsultarCozinhaUnica() {
        given().pathParam("id", 1).when().get("/{id}").then()
                .statusCode(HttpStatus.OK.value()).log().all();
    }

    @Test
    void deveConterQuantidadeAdequadaDeCozinhas_QuandoConsultarCozinhas() {
        given().when().get().then().body("id", hasSize(cozinhasExistentes)).log().all();
    }

    @Test
    void deveConterNomesAdequados_QuandoConsultarCozinhas() {
        given().when().get().then().body("nome", hasItems("Indiana", "Tailandesa")).log().all();
    }


    @Test
    void deveCadastrar_QuandoCozinhaValida() {
        Cozinha cozinha = createCozinha("Japonesa");

        cozinha = cozinhaService.salvar(cozinha);

        assertThat(cozinha).isNotNull();
        assertThat(cozinha.getId()).isNotNull();
    }

    @Test
    void deveFalhar_QuandoCozinhaInvalida() {
        Cozinha cozinha = createCozinha(null);

        ConstraintViolationException exception =
                assertThrows(ConstraintViolationException.class, () -> cozinhaService.salvar(cozinha));

        assertThat(exception).isNotNull();
    }

    @Test
    void deveFalhar_QuandoExcluirCozinhaEmUso() {
        Cozinha cozinha = cozinhaService.buscarOuFalhar(1L);
        long id = cozinha.getId();

        EntidadeEmUsoException exception = assertThrows(
                EntidadeEmUsoException.class, () -> cozinhaService.remover(id));

        assertThat(exception).isNotNull();
    }

    @Test
    void deveRetornarStatus404ECorpoCorretos_QuandoConsultarCozinhaComIdInexistente() {
        String message = String.format("Cozinha de código %d não foi encontrada.", getIdInexistente());

        given().pathParam("id", getIdInexistente()).when().get("/{id}").then()

                .body("title", equalTo(RECURSO_NAO_ENCONTRADO.getTitle()))
                .body("detail", equalTo(message))

                .statusCode(HttpStatus.NOT_FOUND.value()).log().all();

    }

    @Test
    void deveFalhar_QuandoExcluirCozinhaInexistente() {
        long idInexistente = getIdInexistente();

        EntidadeNaoEncontradaException exception =
                assertThrows(EntidadeNaoEncontradaException.class, () -> cozinhaService.remover(idInexistente));

        assertThat(exception).isNotNull();
    }

    private Cozinha createCozinha(String nome) {
        Cozinha cozinha = new Cozinha();
        cozinha.setNome(nome);
        return cozinha;
    }

    private void prepararDados() {
        Cozinha cozinha1 = new Cozinha();
        cozinha1.setNome("Alemâ");
        cozinhaRepository.save(cozinha1);

        Cozinha cozinha = new Cozinha();
        cozinha.setNome("Americana");
        cozinhaRepository.save(cozinha);

        cozinhasExistentes = (int) cozinhaRepository.count();
    }

    private long getIdInexistente() {
        return cozinhaRepository.count() + 1;
    }

    //TOSEE
//    @Test
//    void deveFalhar_QuandoExcluirCozinhaEmUso2() {
//        List<Cozinha> cozinhaStream =
//                cozinhaRepository.findAll().stream().filter(cozinha -> cozinha.getRestaurantes().size() >= 0).toList();
//        Long id = cozinhaStream.get(0).getId();
//
//        EntidadeEmUsoException exception =
//                assertThrows(EntidadeEmUsoException.class, () -> cozinhaService.remover(id));
//
//        assertThat(exception).isNotNull();
//    }

}
