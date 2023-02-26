package algafood.projetoteste.api.controller;

import algafood.projetoteste.api.processor.PatchProcessor;
import algafood.projetoteste.domain.model.Cozinha;
import algafood.projetoteste.domain.repository.CozinhaRepository;
import algafood.projetoteste.domain.service.CozinhaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.beans.BeanUtils.copyProperties;

@RestController
@RequestMapping("/cozinhas")
@RequiredArgsConstructor
public class CozinhaController {

    private final CozinhaRepository cozinhaRepository;
    private final CozinhaService cozinhaService;
    private final PatchProcessor patchProcessor;

    @GetMapping
    public List<Cozinha> listar() {
        return cozinhaRepository.findAll();
    }

    @GetMapping("/{id}")
    public Cozinha buscarPorId(@PathVariable Long id) {
        return cozinhaService.buscarOuFalhar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha adicionar(@RequestBody Cozinha cozinha) {
        return cozinhaService.salvar(cozinha);
    }

    @PutMapping("/{id}")
    public Cozinha atualizar(@PathVariable Long id, @RequestBody Cozinha newCozinha) {
        Cozinha cozinha = cozinhaService.buscarOuFalhar(id);
        copyProperties(newCozinha, cozinha, "id");
        return cozinhaService.salvar(cozinha);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        cozinhaService.remover(id);
    }

    @PatchMapping("/{id}")
    public Cozinha atualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos) {
        Cozinha cozinha = cozinhaService.buscarOuFalhar(id);
        patchProcessor.merge(campos, cozinha);
        return cozinhaService.salvar(cozinha);
    }

}
