package algafood.projetoteste.api.controller;

import algafood.projetoteste.domain.exception.EntidadeNaoEncontradaException;
import algafood.projetoteste.domain.model.Restaurante;
import algafood.projetoteste.domain.repository.RestauranteRepository;
import algafood.projetoteste.domain.service.RestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static org.springframework.beans.BeanUtils.copyProperties;

@RestController
@RequestMapping("/restaurantes")
@AllArgsConstructor
public class RestauranteController {

    private RestauranteRepository restauranteRepository;
    private RestauranteService restauranteService;

    @GetMapping
    public List<Restaurante> listar() {
        return restauranteRepository.findAll();
    }

    @GetMapping("/{id}")
    public Restaurante buscarPorId(@PathVariable Long id) {
        return restauranteRepository.findById(id).get();
    }

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(restauranteService.salvar(restaurante));
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Restaurante atualizar(@PathVariable Long id, @RequestBody Restaurante newRestaurante) {
        var restaurante = restauranteRepository.findOrFail(id);
        copyProperties(newRestaurante, restaurante,
                "id", "formasPagamento", "endereco", "dataCadastro", "produtos");
        return restauranteService.salvar(restaurante);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    public ResponseEntity<?> removerPorId(Long id) {
        try {
            restauranteService.remover(id);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos) {
        var restauranteAtual = restauranteRepository.findById(id);
        if (restauranteAtual.isEmpty()) return ResponseEntity.notFound().build();
        merge(campos, restauranteAtual.get());
        return ResponseEntity.ok(atualizar(id, restauranteAtual.get()));
    }

    private void merge(Map<String, Object> campos, Restaurante restaurante) {
        ObjectMapper objectMapper = new ObjectMapper();
        var restauranteOrigem = objectMapper.convertValue(campos, Restaurante.class);
        campos.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Restaurante.class, key);
            field.setAccessible(true);
            var novoValor = ReflectionUtils.getField(field, restauranteOrigem);
            ReflectionUtils.setField(field, restaurante, novoValor);
        });
    }

}
