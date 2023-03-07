package algafood.projetoteste.api.controller;

import algafood.projetoteste.api.processor.PatchProcessor;
import algafood.projetoteste.domain.model.Cidade;
import algafood.projetoteste.domain.repository.CidadeRepository;
import algafood.projetoteste.domain.service.CidadeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.beans.BeanUtils.copyProperties;

@RestController
@RequestMapping("/cidades")
@RequiredArgsConstructor
public class CidadeController {

    private final CidadeService cidadeService;
    private final CidadeRepository cidadeRepository;
    private final PatchProcessor patchProcessor;

    @GetMapping
    public List<Cidade> listar() {
        return cidadeRepository.findAll();
    }

    @GetMapping("/{id}")
    public Cidade buscar(@PathVariable Long id) {
        return cidadeService.buscarOuFalhar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cidade adicionar(@RequestBody @Valid Cidade cidade) {
        return cidadeService.salvar(cidade);
    }

    @PutMapping("/{id}")
    public Cidade atualizar(@PathVariable Long id, @RequestBody @Valid Cidade newCidade) {
        var cidade = cidadeService.buscarOuFalhar(id);
        copyProperties(newCidade, cidade, "id");
        return cidadeService.salvar(cidade);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        cidadeService.remover(id);
    }

    @PatchMapping("{id}")
    public Cidade atualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos, HttpServletRequest request) {
        Cidade cidade = cidadeService.buscarOuFalhar(id);
        patchProcessor.merge(campos, cidade, request);
        return cidadeService.salvar(cidade);
    }

}
