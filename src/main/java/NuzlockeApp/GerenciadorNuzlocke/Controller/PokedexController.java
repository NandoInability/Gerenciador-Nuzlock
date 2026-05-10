package NuzlockeApp.GerenciadorNuzlocke.Controller;

import NuzlockeApp.GerenciadorNuzlocke.entity.Pokemon;
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

    public PokedexController(PokemonService pokemonService, PokeApiService pokeApiService) {
        this.pokemonService = pokemonService;
        this.pokeApiService = pokeApiService;
    }

    @GetMapping
    public String listarPokemons(Model model, @RequestParam(required = false) String search) {
        List<Pokemon> allPokemons = pokemonService.findAll();
        
        // Se o banco estiver vazio, inicializa com alguns da 1ª gen para não ficar vazio
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
    public String detalhesPokemon(@PathVariable Long id, Model model) {
        Pokemon pokemon = pokemonService.findById(id);
        if (pokemon == null) {
            return "redirect:/pokedex";
        }
        model.addAttribute("pokemon", pokemon);
        return "pokedex/detalhes";
    }
}
