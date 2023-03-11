package algafood.projetoteste;

import algafood.projetoteste.domain.exception.EntidadeEmUsoException;
import algafood.projetoteste.domain.exception.EntidadeNaoEncontradaException;
import algafood.projetoteste.domain.model.Cozinha;
import algafood.projetoteste.domain.repository.CozinhaRepository;
import algafood.projetoteste.domain.service.CozinhaService;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class CozinhaServiceIntegrationTests {

    @Autowired
    private CozinhaService cozinhaService;
    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Test
    void deveCadastrar_QuandoCozinhaValida() {
        Cozinha cozinha = new Cozinha();
        cozinha.setNome("Japonesa");

        cozinha = cozinhaService.salvar(cozinha);

        assertThat(cozinha).isNotNull();
        assertThat(cozinha.getId()).isNotNull();
    }
    @Test
    void deveFalhar_QuandoCozinhaInvalida() {
        Cozinha cozinha = new Cozinha();
        cozinha.setNome(null);

        ConstraintViolationException exception =
                assertThrows(ConstraintViolationException.class, () -> cozinhaService.salvar(cozinha));

        assertThat(exception).isNotNull();
    }
    @Test
    void deveFalhar_QuandoExcluirCozinhaEmUso() {
        Cozinha cozinha = cozinhaService.buscarOuFalhar(1L);
        long id = cozinha.getId();

        EntidadeEmUsoException exception =
                assertThrows(EntidadeEmUsoException.class, () -> cozinhaService.remover(id));

        assertThat(exception).isNotNull();
    }

    @Test
    void deveFalhar_QuandoExcluirCozinhaInexistente() {
        long idInexistente = cozinhaRepository.count() + 1;

        EntidadeNaoEncontradaException exception =
                assertThrows(EntidadeNaoEncontradaException.class, () -> cozinhaService.remover(idInexistente));

        assertThat(exception).isNotNull();
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
