package algafood.projetoteste.domain.repository;

import algafood.projetoteste.domain.model.Cozinha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CozinhaRepository extends JpaRepository<Cozinha, Long> {
    List<Cozinha> findAllByNomeContaining(String nome);

     Optional<Cozinha> findFirstByNomeContaining(String nome);

}
