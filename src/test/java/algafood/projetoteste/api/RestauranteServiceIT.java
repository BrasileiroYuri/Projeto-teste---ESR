package algafood.projetoteste.api;

import algafood.projetoteste.api.exceptionhandler.ApiExceptionHandler;
import algafood.projetoteste.domain.exception.NegocioException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RestauranteServiceIT {

    private ApiExceptionHandler exceptionHandler;

    @Test
    void ji() {
        ApiExceptionHandler apiExceptionHandler = new ApiExceptionHandler();
        apiExceptionHandler.handleNegocioException(new NegocioException(""), null);
    }

}
