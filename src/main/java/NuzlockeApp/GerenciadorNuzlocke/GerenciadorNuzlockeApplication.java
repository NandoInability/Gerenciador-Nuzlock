package NuzlockeApp.GerenciadorNuzlocke;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@SpringBootApplication
@EntityScan("NuzlockeApp.GerenciadorNuzlocke.entity")
@EnableJpaRepositories("NuzlockeApp.GerenciadorNuzlocke.repository")
public class GerenciadorNuzlockeApplication {
    public static void main(String[] args) {
        SpringApplication.run(GerenciadorNuzlockeApplication.class, args);
    }
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }
}