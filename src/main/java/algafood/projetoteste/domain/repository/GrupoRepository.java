package algafood.projetoteste.domain.repository;

import algafood.projetoteste.domain.model.Grupo;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoRepository extends CustomJpaRepository<Grupo, Long> {

}
