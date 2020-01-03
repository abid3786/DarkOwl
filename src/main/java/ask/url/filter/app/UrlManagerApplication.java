package ask.url.filter.app;

import ask.excpt.UrlManagerException;
import ask.model.Progress;
import ask.model.TextLineRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("ask.file.upload, ask.model")
@EntityScan( basePackages = {"ask.model"} )
@EnableJpaRepositories(basePackageClasses = TextLineRepository.class)
public class UrlManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrlManagerApplication.class, args);
	}

}
