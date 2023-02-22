package algafood.projetoteste;

import algafood.projetoteste.infraestructure.repository.CustomJpaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class ProjetoTesteApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjetoTesteApplication.class, args);
    }

}
