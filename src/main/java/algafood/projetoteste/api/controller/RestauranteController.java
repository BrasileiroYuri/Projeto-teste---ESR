package algafood.projetoteste.api.controller;

import algafood.projetoteste.api.model.CozinhaModel;
import algafood.projetoteste.api.model.RestauranteModel;
import algafood.projetoteste.api.processor.MergeProcessor;
import algafood.projetoteste.api.processor.PatchProcessor;
import algafood.projetoteste.domain.model.Restaurante;
import algafood.projetoteste.domain.repository.RestauranteRepository;
import algafood.projetoteste.domain.service.RestauranteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/restaurantes")
@RequiredArgsConstructor
public class RestauranteController {

    private final RestauranteRepository restauranteRepository;
    private final RestauranteService restauranteService;

    private final PatchProcessor patchProcessor;
    private final MergeProcessor mergeProcessor;

//  private final ModelAssembler assembler;


    @GetMapping
    public List<RestauranteModel> listar() {
        List<Restaurante> restaurantes = restauranteRepository.findAll();
        return toCollectionModel(restaurantes);
    }

    @GetMapping("/{id}")
    public RestauranteModel buscarPorId(@PathVariable Long id) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(id);
        return toModel(restaurante);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurante adicionar(@RequestBody @Valid Restaurante restaurante) {
        try {
        return restauranteService.salvar(restaurante);
        } catch (Exception e) {
            return null;
        }
    }

    @PutMapping("/{id}")
    public Restaurante atualizar(@PathVariable Long id, @RequestBody @Valid Restaurante newRestaurante) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(id);
        BeanUtils.copyProperties(
                newRestaurante, restaurante, "id", "formasPagamento", "endereco", "dataCadastro", "produtos");
        return restauranteService.salvar(restaurante);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(Long id) {
        restauranteService.remover(id);
    }

    @PatchMapping("/{id}")
    public Restaurante atualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos,
                                        HttpServletRequest request) {
        var restaurante = restauranteService.buscarOuFalhar(id);
        patchProcessor.merge(campos, restaurante, request);
        mergeProcessor.validate(restaurante);
        return restauranteService.salvar(restaurante);
    }

    private RestauranteModel toModel(Restaurante restaurante) {
        RestauranteModel restauranteModel = new RestauranteModel();

        CozinhaModel cozinhaModel = new CozinhaModel();
        cozinhaModel.setId(restaurante.getCozinha().getId());
        cozinhaModel.setNome(restaurante.getCozinha().getNome());

        restauranteModel.setId(restaurante.getId());
        restauranteModel.setNome(restaurante.getNome());
        restauranteModel.setTaxaFrete(restaurante.getTaxaFrete());
        restauranteModel.setCozinha(cozinhaModel);

        return restauranteModel;
    }

    private List<RestauranteModel> toCollectionModel(List<Restaurante> restaurantes) {
        return restaurantes.stream().map(this::toModel).toList();
    }

}
