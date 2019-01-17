package dk.cngroup.messagecenter;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class MessageCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessageCenterApplication.class, args);
	}
}

