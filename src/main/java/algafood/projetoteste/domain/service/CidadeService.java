package algafood.projetoteste.domain.service;

import algafood.projetoteste.domain.exception.EntidadeEmUsoException;
import algafood.projetoteste.domain.exception.EntidadeNaoEncontradaException;
import algafood.projetoteste.domain.model.Cidade;
import algafood.projetoteste.domain.model.Estado;
import algafood.projetoteste.domain.repository.CidadeRepository;
import algafood.projetoteste.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    public Cidade salvar(Cidade cidade) {
        Estado estado = estadoRepository.buscar(cidade.getEstado().getId());
        if (estado == null)
            throw new EntidadeNaoEncontradaException("O objeto aninhando Estado não existe.");
        cidade.setEstado(estado);
        return cidadeRepository.save(cidade);
    }

    public void remover(Long id) {
        try {
            cidadeRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException("Cidade inexistente");
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException("Cidade não pode ser removida");
        }
    }

}
