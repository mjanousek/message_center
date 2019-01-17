package dk.cngroup.messagecenter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("dk.cngroup.messagecenter.data")
@PropertySource("application.properties")
public class DataConfig {
}
