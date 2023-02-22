package algafood.projetoteste.domain.service;

import algafood.projetoteste.domain.model.Cozinha;
import algafood.projetoteste.domain.model.Restaurante;
import algafood.projetoteste.domain.repository.CozinhaRepository;
import algafood.projetoteste.domain.repository.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestauranteService {

    private final RestauranteRepository restauranteRepository;

    private final CozinhaRepository cozinhaRepository;

    public Restaurante salvar(Restaurante restaurante) {
        Cozinha cozinha = cozinhaRepository
                .findOrFail(restaurante.getCozinha().getId());
        restaurante.setCozinha(cozinha);
        return restauranteRepository.save(restaurante);
    }

    public void remover(Long id) {
        restauranteRepository.deleteById(id);
    }

}
