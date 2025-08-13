package NuzlockeApp.GerenciadorNuzlocke;

import NuzlockeApp.GerenciadorNuzlocke.entity.Pokemon;
import NuzlockeApp.GerenciadorNuzlocke.repository.RepdePkm;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(RepdePkm repository) {
        return args -> {
            Pokemon bulbasaur = new Pokemon();
            bulbasaur.setName("Bulbasaur");
            bulbasaur.setPrimaryType("Grass");
            bulbasaur.setSecundaryType("Poison");
            bulbasaur.setGeneration(1);
            bulbasaur.setPkdexNumber(1);
            repository.save(bulbasaur);

            Pokemon charmander = new Pokemon();
            charmander.setName("Charmander");
            charmander.setPrimaryType("Fire");
            charmander.setGeneration(1);
            charmander.setPkdexNumber(4);
            repository.save(charmander);

            Pokemon Squirtle = new Pokemon();
            Squirtle.setName("Squirtle");
            Squirtle.setPrimaryType("Water");
            Squirtle.setGeneration(1);
            Squirtle.setPkdexNumber(7);
            repository.save(Squirtle);
        };
    }
}