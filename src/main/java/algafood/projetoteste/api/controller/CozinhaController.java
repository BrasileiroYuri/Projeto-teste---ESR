package algafood.projetoteste.api.controller;

import algafood.projetoteste.domain.model.Cozinha;
import algafood.projetoteste.domain.repository.CozinhaRepository;
import algafood.projetoteste.domain.service.CozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;
    @Autowired
    private CozinhaService cozinhaService;

    @GetMapping
    public List<Cozinha> listar() {
        return cozinhaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        var cozinha = cozinhaRepository.findById(id);
        if (cozinha.isPresent()) return ResponseEntity.ok(cozinha);
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha adicionar(@RequestBody Cozinha cozinha) {
        return cozinhaService.salvar(cozinha);
    }

    @PutMapping("/{cozinhaId}")
    public ResponseEntity<?> atualizar(@PathVariable Long cozinhaId, @RequestBody Cozinha newCozinha) {
        var cozinha = cozinhaRepository.findById(cozinhaId);
        if (cozinha.isPresent()) return ResponseEntity.notFound().build();
        copyProperties(newCozinha, cozinha, "id");
        return ResponseEntity.status(HttpStatus.OK).body(cozinhaService.salvar(cozinha.get()));
    }

    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cozinhaId) {
        cozinhaService.remover(cozinhaId);
    }

}
