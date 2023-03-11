package algafood.projetoteste.api.exceptionhandler;

import algafood.projetoteste.domain.model.Cozinha;
import algafood.projetoteste.domain.model.Restaurante;
import algafood.projetoteste.domain.service.RestauranteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.verify;

@SpringBootTest
public class RestauranteServiceTest {

    @MockBean
    private RestauranteService restauranteService;

    @Mock
    private Restaurante restauranteMock;

    @Mock
    private Cozinha cozinhaMock;

    @Test
    @DisplayName("Deve salvar com sucesso quando o objeto estiver válido.")
    public void deveSalvarObjetoComSucesso_QuandoObjetoEstiverValido() {
        restauranteService.salvar(restauranteMock);
        verify(restauranteService).salvar(restauranteMock);

    }

}
