package algafood.projetoteste.domain.service;

import algafood.projetoteste.domain.exception.EntidadeEmUsoException;
import algafood.projetoteste.domain.exception.EntidadeNaoEncontradaException;
import algafood.projetoteste.domain.model.Cidade;
import algafood.projetoteste.domain.model.Estado;
import algafood.projetoteste.domain.repository.CidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CidadeService {

    public static final String NO_ENTITY_FOR_ID = "No entity for id %d";

    private final CidadeRepository cidadeRepository;

    private final EstadoService estadoService;

    public Cidade salvar(Cidade cidade) {
        Long estadoId = cidade.getEstado().getId();
        Estado estado = estadoService.buscarOuFalhar(estadoId);
        cidade.setEstado(estado);
        return cidadeRepository.save(cidade);
    }

    public void remover(Long id) {
        try {
            cidadeRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                    format("Cidade de id %d inexistente", id));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    format("Cidade de id %d pode ser removida", id));
        }
    }

    public Cidade buscarOuFalhar(Long id) {
        return cidadeRepository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(format(NO_ENTITY_FOR_ID, id)));
    }

}
