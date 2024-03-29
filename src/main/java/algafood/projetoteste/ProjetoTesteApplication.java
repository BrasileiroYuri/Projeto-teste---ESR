package algafood.projetoteste;

import algafood.projetoteste.infraestructure.repository.CustomJpaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.TimeZone;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class ProjetoTesteApplication {

    public static final String UTC = "UTC";

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone(UTC));
        SpringApplication.run(ProjetoTesteApplication.class, args);
    }

}
