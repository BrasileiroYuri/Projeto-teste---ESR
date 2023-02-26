package algafood.projetoteste.domain.service;

import algafood.projetoteste.domain.exception.EntidadeNaoEncontradaException;
import algafood.projetoteste.domain.model.Cozinha;
import algafood.projetoteste.domain.model.Restaurante;
import algafood.projetoteste.domain.repository.CozinhaRepository;
import algafood.projetoteste.domain.repository.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class RestauranteService {

    public static final String NO_ENTITY_FOR_ID = "No entity for id %d";

    private final RestauranteRepository restauranteRepository;
    private final CozinhaRepository cozinhaRepository;
    private final CozinhaService cozinhaService;

    public Restaurante salvar(Restaurante restaurante) {
        Cozinha cozinha = cozinhaService.buscarOuFalhar(restaurante.getCozinha().getId());
        restaurante.setCozinha(cozinha);
        return restauranteRepository.save(restaurante);
    }

    public void remover(Long id) {
        restauranteRepository.deleteById(id);
    }

    public Restaurante buscarOuFalhar(Long id) {
        return restauranteRepository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(format(NO_ENTITY_FOR_ID, id)));
    }

}
