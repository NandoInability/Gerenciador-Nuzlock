package NuzlockeApp.GerenciadorNuzlocke;

import NuzlockeApp.GerenciadorNuzlocke.entity.Pokemon;
import NuzlockeApp.GerenciadorNuzlocke.entity.Jogo;
import NuzlockeApp.GerenciadorNuzlocke.repository.JogoRepository;
import NuzlockeApp.GerenciadorNuzlocke.repository.RepdePkm;
import NuzlockeApp.GerenciadorNuzlocke.repository.RepPKMCapturado;
import NuzlockeApp.GerenciadorNuzlocke.service.PokeApiService; // Importe o novo serviço

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(RepdePkm pokemonRepository,
                                   JogoRepository jogoRepository,
                                   RepPKMCapturado repPKMCapturado,
                                   PokeApiService pokeApiService) { // <--- Injete o PokeApiService
        return args -> {
            // Lógica para carregar Pokemons apenas se não existirem
            if (pokemonRepository.count() == 0) {
                System.out.println("Banco de dados 'pokemons' vazio. Carregando Pokémons da PokeAPI...");

                // Define quantos Pokémons você quer carregar. Ex: 1025 para até a Geração 9 (excluindo formas regionais/mega)
                List<Pokemon> allPokemons = pokeApiService.fetchAndMapPokemons(1025);

                if (!allPokemons.isEmpty()) {
                    pokemonRepository.saveAll(allPokemons);
                    System.out.println("Pokémons da PokeAPI carregados com sucesso! Total: " + allPokemons.size());
                } else {
                    System.err.println("Nenhum Pokémon foi carregado da PokeAPI.");
                }

            } else {
                System.out.println("Tabela 'pokemons' já contém dados. Pulando carregamento inicial de Pokémons.");
            }

            // ... (Seus outros códigos para carregar Jogos e PKMs Capturados) ...
            if (jogoRepository.count() == 0) {
                System.out.println("Banco de dados 'jogos' vazio. Carregando Jogos iniciais...");

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
                System.out.println("Jogos iniciais carregados com sucesso!");
            } else {
                System.out.println("Tabela 'jogos' já contém dados. Pulando carregamento inicial de Jogos.");
            }

            if (repPKMCapturado.count() == 0) {
                System.out.println("Tabela 'pkmcapturado' vazia. Carregando dados de PKMs capturados (se houver dependências)...");
                System.out.println("Dados de PKMs capturados carregados (se implementado e necessário).");
            } else {
                System.out.println("Tabela 'pkmcapturado' já contém dados. Pulando carregamento inicial de PKMs capturados.");
            }
        };
    }
}