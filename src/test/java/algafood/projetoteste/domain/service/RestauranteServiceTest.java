package algafood.projetoteste.domain.service;

import algafood.projetoteste.domain.model.Cozinha;
import algafood.projetoteste.domain.model.Restaurante;
import algafood.projetoteste.domain.repository.RestauranteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
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
    @Mock
    private RestauranteRepository restauranteRepository;

    @Test
    @DisplayName("Deve salvar com sucesso quando o objeto estiver válido.")
     void deveSalvarObjetoComSucesso_QuandoObjetoEstiverValido() {
        Restaurante mock = Mockito.mock(Restaurante.class);

        restauranteService.salvar(mock);

        verify(restauranteService).salvar(mock);
    }

}
