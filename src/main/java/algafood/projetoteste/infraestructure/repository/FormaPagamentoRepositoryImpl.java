package algafood.projetoteste.infraestructure.repository;

import algafood.projetoteste.domain.model.FormaPagamento;
import algafood.projetoteste.domain.repository.FormaPagamentoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FormaPagamentoRepositoryImpl implements FormaPagamentoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<FormaPagamento> listar() {
        return entityManager.createQuery("from FormaPagamento", FormaPagamento.class).getResultList();
    }

    @Override
    public FormaPagamento buscar(Long id) {
        return entityManager.find(FormaPagamento.class, id);
    }

    @Override
    public FormaPagamento salvar(FormaPagamento formaPagamento) {
        return entityManager.merge(formaPagamento);
    }

    @Override
    public void remover(Long id) {
        entityManager.remove(buscar(id));
    }

}
