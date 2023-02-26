package algafood.projetoteste.domain.service;

import algafood.projetoteste.domain.exception.EntidadeNaoEncontradaException;
import algafood.projetoteste.domain.model.Estado;
import algafood.projetoteste.domain.repository.EstadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class EstadoService {

    public static final String NO_ENTITY_FOR_ID = "No entity for id %d";
    private final EstadoRepository estadoRepository;

    public Estado salvar(Estado estado) {
        return estadoRepository.save(estado);
    }

    public void remover(Long id) {
        estadoRepository.deleteById(id);
    }

    public Estado buscarOuFalhar(Long id) {
        return estadoRepository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(format(NO_ENTITY_FOR_ID, id)));
    }

}
