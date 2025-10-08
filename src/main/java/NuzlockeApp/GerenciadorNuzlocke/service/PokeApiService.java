package NuzlockeApp.GerenciadorNuzlocke.service;

import NuzlockeApp.GerenciadorNuzlocke.api.pokeapi.*; // Importe TODAS as classes do seu pacote pokeapi
import NuzlockeApp.GerenciadorNuzlocke.entity.Pokemon;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher; // Não é mais necessário, pode remover se quiser
import java.util.regex.Pattern; // Não é mais necessário, pode remover se quiser

@Service
public class PokeApiService {

    private final RestTemplate restTemplate;
    private final String POKEAPI_BASE_URL = "https://pokeapi.co/api/v2/";

    public PokeApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Pokemon> fetchAndMapPokemons(int limit) {
        List<Pokemon> pokemons = new ArrayList<>();
        String url = POKEAPI_BASE_URL + "pokemon?limit=" + limit;

        System.out.println("Chamando PokeAPI para lista de Pokemons: " + url); // DEBUG

        PokemonListResponse listResponse = null;
        try {
            listResponse = restTemplate.getForObject(url, PokemonListResponse.class);
            System.out.println("Resposta da lista recebida. Count total na API: " + (listResponse != null ? listResponse.getCount() : "null") + ", Resultados nesta página: " + (listResponse != null && listResponse.getResults() != null ? listResponse.getResults().size() : "null")); // DEBUG
        } catch (Exception e) {
            System.err.println("Erro ao buscar lista de Pokemons da PokeAPI: " + e.getMessage());
            e.printStackTrace(); // Imprime o stack trace completo
            return pokemons; // Retorna lista vazia em caso de erro
        }

        if (listResponse != null && listResponse.getResults() != null) {
            System.out.println("Iniciando iteração pelos " + listResponse.getResults().size() + " Pokemons da lista para buscar detalhes..."); // DEBUG
            for (PokemonListItem item : listResponse.getResults()) {
                Pokemon pokemon = fetchPokemonDetails(item.getUrl());
                if (pokemon != null) {
                    pokemons.add(pokemon);
                }
            }
        }
        System.out.println("Total de Pokemons mapeados e retornados: " + pokemons.size()); // DEBUG
        return pokemons;
    }

    private Pokemon fetchPokemonDetails(String detailUrl) {
        try {
            System.out.println("Buscando detalhes para: " + detailUrl); // DEBUG
            PokemonDetailResponse detailResponse = restTemplate.getForObject(detailUrl, PokemonDetailResponse.class);

            if (detailResponse != null) {
                Pokemon pokemon = new Pokemon();
                pokemon.setPkdexNumber(detailResponse.getId());
                pokemon.setName(capitalize(detailResponse.getName()));

                // Tipos
                if (detailResponse.getTypes() != null && !detailResponse.getTypes().isEmpty()) {
                    pokemon.setPrimaryType(capitalize(detailResponse.getTypes().get(0).getType().getName()));
                    if (detailResponse.getTypes().size() > 1) {
                        pokemon.setSecundaryType(capitalize(detailResponse.getTypes().get(1).getType().getName()));
                    }
                }

                // Sprite
                if (detailResponse.getSprites() != null) {
                    pokemon.setSprite(detailResponse.getSprites().getFrontDefault());
                }

                // Geração e EvolChain (requer chamadas adicionais)
                if (detailResponse.getSpecies() != null && detailResponse.getSpecies().getUrl() != null) {
                    String speciesUrl = detailResponse.getSpecies().getUrl();

                    try {
                        PokemonSpeciesResponse speciesResponse = restTemplate.getForObject(speciesUrl, PokemonSpeciesResponse.class);
                        if (speciesResponse != null) {
                            // Geração
                            if (speciesResponse.getGeneration() != null && speciesResponse.getGeneration().getName() != null) {
                                // Ex: "generation-1" -> "1"
                                String genName = speciesResponse.getGeneration().getName();
                                try {
                                    // Pega o número após o "generation-"
                                    pokemon.setGeneration(Integer.parseInt(genName.replace("generation-", "")));
                                } catch (NumberFormatException nfe) {
                                    System.err.println("Erro ao parsear número da geração: " + genName);
                                    pokemon.setGeneration(getGenerationFromPokedexNumber(detailResponse.getId())); // Fallback
                                }
                            } else {
                                pokemon.setGeneration(getGenerationFromPokedexNumber(detailResponse.getId())); // Fallback
                            }

                            // EvolChain
                            if (speciesResponse.getEvolutionChain() != null && speciesResponse.getEvolutionChain().getUrl() != null) {
                                String baseEvolName = getBaseEvolutionName(speciesResponse.getEvolutionChain().getUrl());
                                if (baseEvolName != null) {
                                    pokemon.setEvolChain(baseEvolName);
                                } else {
                                    pokemon.setEvolChain(capitalize(detailResponse.getName())); // Fallback
                                }
                            } else {
                                pokemon.setEvolChain(capitalize(detailResponse.getName())); // Fallback
                            }
                        }
                    } catch (Exception e) {
                        System.err.println("Erro ao obter dados de espécie/evolução para " + pokemon.getName() + " (" + speciesUrl + ") - " + e.getMessage());
                        // e.printStackTrace(); // Descomente se quiser ver o stack trace para cada falha de espécie
                        pokemon.setGeneration(getGenerationFromPokedexNumber(detailResponse.getId())); // Fallback
                        pokemon.setEvolChain(capitalize(detailResponse.getName())); // Fallback
                    }
                } else {
                    // Fallback se não houver dados de species
                    pokemon.setGeneration(getGenerationFromPokedexNumber(detailResponse.getId()));
                    pokemon.setEvolChain(capitalize(detailResponse.getName()));
                }

                System.out.println("Mapeado: " + pokemon.getName() + " (ID: " + pokemon.getPkdexNumber() + ", Gen: " + pokemon.getGeneration() + ", EvolChain: " + pokemon.getEvolChain() + ")"); // DEBUG
                return pokemon;
            }
        } catch (Exception e) {
            System.err.println("Erro FATAL ao buscar detalhes do Pokémon de URL: " + detailUrl + " - " + e.getMessage());
            e.printStackTrace(); // Imprime o stack trace completo para falhas de detalhes
        }
        return null;
    }

    // Método para buscar o nome da cadeia de evolução base a partir da URL da cadeia
    private String getBaseEvolutionName(String evolutionChainUrl) {
        try {
            System.out.println("Buscando cadeia de evolução em: " + evolutionChainUrl); // DEBUG
            EvolutionChainResponse evolutionResponse = restTemplate.getForObject(evolutionChainUrl, EvolutionChainResponse.class);
            if (evolutionResponse != null && evolutionResponse.getChain() != null && evolutionResponse.getChain().getSpecies() != null) {
                String baseName = evolutionResponse.getChain().getSpecies().getName();
                System.out.println("Cadeia de evolução base para " + evolutionChainUrl + ": " + baseName); // DEBUG
                return capitalize(baseName);
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar nome da cadeia de evolução em URL: " + evolutionChainUrl + " - " + e.getMessage());
            // e.printStackTrace();
        }
        return null;
    }

    // Helper para capitalizar nomes (ex: "bulbasaur" -> "Bulbasaur")
    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    // Método simples para estimar a geração pelo número da Pokedex (fallback)
    private int getGenerationFromPokedexNumber(int pokedexNumber) {
        if (pokedexNumber <= 151) return 1;
        if (pokedexNumber <= 251) return 2;
        if (pokedexNumber <= 386) return 3;
        if (pokedexNumber <= 493) return 4;
        if (pokedexNumber <= 649) return 5;
        if (pokedexNumber <= 721) return 6;
        if (pokedexNumber <= 809) return 7;
        if (pokedexNumber <= 898) return 8;
        if (pokedexNumber <= 1025) return 9; // Dados de Scarlet/Violet + DLC
        return 0; // Para Pokemons ainda não mapeados ou especiais
    }
}