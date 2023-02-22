package algafood.projetoteste.domain.repository;

import algafood.projetoteste.domain.model.Cidade;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CidadeRepository extends CustomJpaRepository<Cidade, Long> {

    @Query("FROM Cidade c JOIN FETCH c.estado")
    List<Cidade> findAll();

}
