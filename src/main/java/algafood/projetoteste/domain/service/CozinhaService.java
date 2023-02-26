package algafood.projetoteste.domain.service;

import algafood.projetoteste.domain.exception.EntidadeEmUsoException;
import algafood.projetoteste.domain.exception.EntidadeNaoEncontradaException;
import algafood.projetoteste.domain.model.Cozinha;
import algafood.projetoteste.domain.repository.CozinhaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CozinhaService {

    public static final String NO_ENTITY_FOR_ID = "No entity for id %d";
    public final CozinhaRepository cozinhaRepository;

    @Transactional
    public Cozinha salvar(Cozinha cozinha) {
        return cozinhaRepository.save(cozinha);
    }

    @Transactional
    public void remover(Long id) {
        try {
            cozinhaRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                    format("Cozinha de código %d não foi encontrada.", id));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    format("Cozinha de código %d está em uso.", id));
        }
    }

    public Cozinha buscarOuFalhar(Long id) {
        return cozinhaRepository.findById(id).orElseThrow(() ->
                new EntidadeNaoEncontradaException(format(NO_ENTITY_FOR_ID, id)));
    }

}
