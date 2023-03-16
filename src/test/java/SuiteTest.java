import algafood.projetoteste.domain.service.CozinhaServiceIT;
import algafood.projetoteste.domain.service.RestauranteServiceIT;
import algafood.projetoteste.domain.service.RestauranteServiceTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        CozinhaServiceIT.class,
        RestauranteServiceIT.class,
        RestauranteServiceTest.class
})
public class SuiteTest {
}
