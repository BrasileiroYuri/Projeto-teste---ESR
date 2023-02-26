package algafood.projetoteste.api.controller;

import algafood.projetoteste.api.processor.PatchProcessor;
import algafood.projetoteste.domain.model.Restaurante;
import algafood.projetoteste.domain.repository.RestauranteRepository;
import algafood.projetoteste.domain.service.RestauranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.beans.BeanUtils.copyProperties;

@RestController
@RequestMapping("/restaurantes")
@RequiredArgsConstructor
public class RestauranteController {

    private final RestauranteRepository restauranteRepository;
    private final RestauranteService restauranteService;
    private final PatchProcessor patchProcessor;

    @GetMapping
    public List<Restaurante> listar() {
        return restauranteRepository.findAll();
    }

    @GetMapping("/{id}")
    public Restaurante buscarPorId(@PathVariable Long id) {
        return restauranteService.buscarOuFalhar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurante adicionar(@RequestBody Restaurante restaurante) {
        return restauranteService.salvar(restaurante);
    }

    @PutMapping("/{id}")
    public Restaurante atualizar(@PathVariable Long id, @RequestBody Restaurante newRestaurante) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(id);
        copyProperties(newRestaurante, restaurante,
                "id", "formasPagamento", "endereco", "dataCadastro", "produtos");
        return restauranteService.salvar(restaurante);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(Long id) {
        restauranteService.remover(id);
    }

    @PatchMapping("/{id}")
    public Restaurante atualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos) {
        var restaurante = restauranteService.buscarOuFalhar(id);
        patchProcessor.merge(campos, restaurante);
        return restauranteService.salvar(restaurante);
    }

}
