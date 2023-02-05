package algafood.projetoteste.api.controller;

import algafood.projetoteste.domain.model.Cozinha;
import algafood.projetoteste.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @GetMapping
    public List<Cozinha> listar() {
        return cozinhaRepository.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cozinha> buscarPorId(@PathVariable Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.HOST, "algaworks.com");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(cozinhaRepository.buscar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Cozinha adicionar(@RequestBody Cozinha cozinha) {
        return cozinhaRepository.salvar(cozinha);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(Long id) {
        cozinhaRepository.remover(id);
    }

}
