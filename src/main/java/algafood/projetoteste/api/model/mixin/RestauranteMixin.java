package algafood.projetoteste.api.model.mixin;

import algafood.projetoteste.domain.model.Cozinha;
import algafood.projetoteste.domain.model.Endereco;
import algafood.projetoteste.domain.model.FormaPagamento;
import algafood.projetoteste.domain.model.Produto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

public class RestauranteMixin {

    @JsonIgnoreProperties(value = "nome", allowGetters = true)
    private Cozinha cozinha;

    @JsonIgnore
    private List<FormaPagamento> formasPagamento;

    @JsonIgnore
    private Endereco endereco;

    @JsonIgnore
    private List<Produto> produtos;

}
