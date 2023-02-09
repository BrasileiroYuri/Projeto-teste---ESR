package algafood.projetoteste.domain.service;

import algafood.projetoteste.domain.exception.EntidadeEmUsoException;
import algafood.projetoteste.domain.exception.EntidadeNaoEncontradaException;
import algafood.projetoteste.domain.model.Restaurante;
import algafood.projetoteste.domain.repository.CozinhaRepository;
import algafood.projetoteste.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;
    @Autowired
    private CozinhaRepository cozinhaRepository;

    public Restaurante salvar(Restaurante restaurante) {
        var cozinha = cozinhaRepository.findById(restaurante.getCozinha().getId());
        if (cozinha.isEmpty())
            throw new EntidadeNaoEncontradaException("Cozinha de id %d não encontrada.");
        restaurante.setCozinha(cozinha.get());
        return restauranteRepository.salvar(restaurante);
    }

    public void remover(Long id) {
        try {
            restauranteRepository.remover(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException("Restaurante inexistente.");
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException("Este recurso não pode ser excluído.");
        }
    }

}
