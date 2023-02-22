package algafood.projetoteste.domain.repository;

import algafood.projetoteste.domain.model.Permissao;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissaoRepository extends CustomJpaRepository<Permissao, Long> {

}

