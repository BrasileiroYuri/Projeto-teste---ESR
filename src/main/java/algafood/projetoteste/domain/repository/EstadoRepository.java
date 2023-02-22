package algafood.projetoteste.domain.repository;

import algafood.projetoteste.domain.model.Estado;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository extends CustomJpaRepository<Estado, Long> {

}
