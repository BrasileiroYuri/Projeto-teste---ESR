package algafood.projetoteste.domain.service;

import algafood.projetoteste.domain.exception.EntidadeEmUsoException;
import algafood.projetoteste.domain.exception.EntidadeNaoEncontradaException;
import algafood.projetoteste.domain.model.Estado;
import algafood.projetoteste.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class EstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    public Estado salvar(Estado estado) {
        return estadoRepository.salvar(estado);
    }

    public void remover(Long id) {
        try {
            estadoRepository.remover(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException("Estado não encontrado");
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException("Estado está em uso");
        }
    }

}
