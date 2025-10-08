package NuzlockeApp.GerenciadorNuzlocke.api.pokeapi;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PokemonSpeciesResponse {
    @JsonProperty("evolution_chain")
    private EvolutionChainLink evolutionChain;

    // Mude de int para NamedApiResource
    private NamedApiResource generation; // <--- CORREÇÃO AQUI

    // Getters e Setters
    public EvolutionChainLink getEvolutionChain() { return evolutionChain; }
    public void setEvolutionChain(EvolutionChainLink evolutionChain) { this.evolutionChain = evolutionChain; }

    public NamedApiResource getGeneration() { return generation; } // <--- CORREÇÃO AQUI
    public void setGeneration(NamedApiResource generation) { this.generation = generation; } // <--- CORREÇÃO AQUI
}