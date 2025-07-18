package EasyBytes.SpringBoot.SchoolApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories("EasyBytes.SpringBoot.SchoolApp.repository")
@EntityScan("EasyBytes.SpringBoot.SchoolApp.model")
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class SchoolApp {

	public static void main(String[] args) {
		SpringApplication.run(SchoolApp.class, args);
	}

}
