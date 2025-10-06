package NuzlockeApp.GerenciadorNuzlocke;

import NuzlockeApp.GerenciadorNuzlocke.entity.Pokemon;
import NuzlockeApp.GerenciadorNuzlocke.entity.Jogo;
import NuzlockeApp.GerenciadorNuzlocke.repository.JogoRepository;
import NuzlockeApp.GerenciadorNuzlocke.repository.RepdePkm;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(RepdePkm repository, JogoRepository jogoRepository) {
        return args -> {
            repository.deleteAll();
                Pokemon bulbasaur = new Pokemon();
                bulbasaur.setName("Bulbasaur");
                bulbasaur.setPrimaryType("Grass");
                bulbasaur.setSecundaryType("Poison");
                bulbasaur.setGeneration(1);
                bulbasaur.setPkdexNumber(1);
                bulbasaur.setSprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png");
                bulbasaur.setEvolChain("Bulbasaur");
                repository.save(bulbasaur);

                Pokemon ivysaur = new Pokemon();
                ivysaur.setName("Ivysaur");
                ivysaur.setPrimaryType("Grass");
                ivysaur.setSecundaryType("Poison");
                ivysaur.setGeneration(1);
                ivysaur.setPkdexNumber(2);
                ivysaur.setSprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/2.png");
                ivysaur.setEvolChain("Bulbasaur");
                repository.save(ivysaur);

                Pokemon charmander = new Pokemon();
                charmander.setName("Charmander");
                charmander.setPrimaryType("Fire");
                charmander.setGeneration(1);
                charmander.setPkdexNumber(4);
                charmander.setSprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/4.png");
                charmander.setEvolChain("Charmander");
                repository.save(charmander);

                Pokemon squirtle = new Pokemon();
                squirtle.setName("Squirtle");
                squirtle.setPrimaryType("Water");
                squirtle.setGeneration(1);
                squirtle.setPkdexNumber(7);
                squirtle.setSprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/7.png");
                squirtle.setEvolChain("Squirtle");
                repository.save(squirtle);

                Pokemon cartepie = new Pokemon();
                cartepie.setName("Cartepie");
                cartepie.setPrimaryType("Bug");
                cartepie.setGeneration(1);
                cartepie.setSprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/10.png");
                cartepie.setEvolChain("Cartepie");
                repository.save(cartepie);

            Pokemon weedle = new Pokemon();
            weedle.setName("Weedle");
            weedle.setPrimaryType("Bug");
            weedle.setSecundaryType("Poison");
            weedle.setGeneration(1);
            weedle.setSprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/13.png");
            weedle.setEvolChain("Weedle");
            repository.save(weedle);


                System.out.println("Dados iniciais carregados com sucesso!");

            if (jogoRepository.count() == 0) {
                Jogo Black2 = new Jogo();
                Black2.setNome("Pokemon Black 2");
                Black2.setGeracao(5);
                jogoRepository.save(Black2);

                Jogo platinum = new Jogo();
                platinum.setNome("Pokémon Platinum");
                platinum.setGeracao(4);
                jogoRepository.save(platinum);

                Jogo heartGold = new Jogo();
                heartGold.setNome("Pokémon HeartGold");
                heartGold.setGeracao(4);
                jogoRepository.save(heartGold);
            }
        };
    }
}