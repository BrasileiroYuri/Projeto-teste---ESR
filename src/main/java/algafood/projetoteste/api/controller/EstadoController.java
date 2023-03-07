package algafood.projetoteste.api.controller;

import algafood.projetoteste.domain.model.Estado;
import algafood.projetoteste.domain.repository.EstadoRepository;
import algafood.projetoteste.domain.service.EstadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@RestController
@RequestMapping("/estados")
@RequiredArgsConstructor
public class EstadoController {

    private final EstadoRepository estadoRepository;
    private final EstadoService estadoService;

    @GetMapping
    public List<Estado> listar() {
        return estadoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Estado buscarPorId(@PathVariable Long id) {
        return estadoService.buscarOuFalhar(id);
    }

    @PutMapping("/{id}")
    public Estado atualizar(@PathVariable Long id, @RequestBody @Valid Estado newEstado) {
        Estado estado = estadoService.buscarOuFalhar(id);
        copyProperties(newEstado, estado, "id");
        return estadoService.salvar(estado);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Estado adicionar(@RequestBody @Valid Estado estado) {
        return estadoService.salvar(estado);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        estadoService.remover(id);
    }

}
