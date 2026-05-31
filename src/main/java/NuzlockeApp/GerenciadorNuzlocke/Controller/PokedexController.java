package NuzlockeApp.GerenciadorNuzlocke.Controller;

import NuzlockeApp.GerenciadorNuzlocke.entity.Pokemon;
import NuzlockeApp.GerenciadorNuzlocke.repository.RepdePkm;
import NuzlockeApp.GerenciadorNuzlocke.service.PokeApiService;
import NuzlockeApp.GerenciadorNuzlocke.service.PokemonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/pokedex")
public class PokedexController {

    private final PokemonService pokemonService;
    private final PokeApiService pokeApiService;

    // Injetamos também o seu repositório para usar a busca otimizada
    private final RepdePkm repdePkm;

    public PokedexController(PokemonService pokemonService, PokeApiService pokeApiService, RepdePkm repdePkm) {
        this.pokemonService = pokemonService;
        this.pokeApiService = pokeApiService;
        this.repdePkm = repdePkm;
    }

    @GetMapping
    public String listarPokemons(Model model, @RequestParam(required = false) String search) {
        // Usa findAll() aqui pois a pokedex precisa dos dados completos
        List<Pokemon> allPokemons = pokemonService.findAll();

        if (allPokemons.isEmpty()) {
            allPokemons = pokeApiService.fetchAndMapPokemons(151);
            allPokemons.forEach(pokemonService::save);
        }
        if (search != null && !search.isEmpty()) {
            String finalSearch = search.toLowerCase();
            allPokemons = allPokemons.stream()
                    .filter(p -> p.getName().toLowerCase().contains(finalSearch) ||
                            String.valueOf(p.getPkdexNumber()).equals(finalSearch))
                    .collect(Collectors.toList());
        }
        model.addAttribute("pokemons", allPokemons);
        model.addAttribute("search", search);
        return "pokedex/lista";
    }

    @GetMapping("/{id}")
    public String detalhesPokemon(@PathVariable Integer id, Model model) {
        // Usa findByPkdexNumber que busca pelo número da pokédex
        Pokemon pokemon = repdePkm.findByIdComEvolucoes(id)
                .orElseThrow(() -> new IllegalArgumentException("Pokémon não encontrado"));
        model.addAttribute("pokemon", pokemon);
        return "pokedex/detalhes";
    }
}
