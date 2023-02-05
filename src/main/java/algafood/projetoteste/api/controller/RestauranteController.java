package algafood.projetoteste.api.controller;

import algafood.projetoteste.domain.model.Restaurante;
import algafood.projetoteste.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    public List<Restaurante> listar() {
        return restauranteRepository.listar();
    }

    @GetMapping("/{id}")
    public Restaurante buscarPorId(@PathVariable Long id) {
        return restauranteRepository.buscar(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Restaurante adicionar(@RequestBody Restaurante restaurante) {
        return restauranteRepository.salvar(restaurante);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    public void removerPorId(Long id) {
        restauranteRepository.remover(id);
    }

}
