package algafood.projetoteste.domain.model;

import algafood.projetoteste.core.validation.Groups;
import algafood.projetoteste.core.constraints.IdValidation;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Estado {

    @Id
    @IdValidation(groups = Groups.EstadoId.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nome;

}
