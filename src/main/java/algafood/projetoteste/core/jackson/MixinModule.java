package algafood.projetoteste.core.jackson;

import algafood.projetoteste.api.model.mixin.CidadeMixin;
import algafood.projetoteste.api.model.mixin.CozinhaMixin;
import algafood.projetoteste.api.model.mixin.RestauranteMixin;
import algafood.projetoteste.domain.model.Cidade;
import algafood.projetoteste.domain.model.Cozinha;
import algafood.projetoteste.domain.model.Restaurante;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Component;

@Component
public class MixinModule extends SimpleModule {

    public MixinModule() {
        setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
        setMixInAnnotation(Cidade.class, CidadeMixin.class);
        setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
    }

}
