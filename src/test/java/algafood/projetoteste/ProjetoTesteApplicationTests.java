package algafood.projetoteste;

import algafood.projetoteste.domain.model.Cozinha;
import algafood.projetoteste.domain.model.Restaurante;
import algafood.projetoteste.domain.service.RestauranteService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class ProjetoTesteApplicationTests {

    @Autowired
    private RestauranteService restauranteService;

    @Mock
    private Restaurante mock;
    @Test
    public void contextLoads() {
        Restaurante mock = Mockito.mock(Restaurante.class);
        RestauranteService mock2 = Mockito.mock(RestauranteService.class);
        Cozinha cozinha = new Cozinha();
        mock.setCozinha(cozinha);
        mock.setId(1L);
        mock.setNome("Comida Japonesa");
        mock.setTaxaFrete(BigDecimal.valueOf(30.00));
        mock2.salvar(mock);
        Mockito.verify(mock2).salvar(mock);
    }

}
