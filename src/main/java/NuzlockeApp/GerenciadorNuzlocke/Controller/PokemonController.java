package NuzlockeApp.GerenciadorNuzlocke.Controller;

import NuzlockeApp.GerenciadorNuzlocke.entity.Pokemon;
import NuzlockeApp.GerenciadorNuzlocke.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pokemons")
public class PokemonController {
    // 3. Injetamos o PokemonService. O controller agora fala com o "cozinheiro".
    @Autowired
    private PokemonService pokemonService;

    @GetMapping
    // 4. O método agora retorna um ResponseEntity, que "envelopa" nossa lista.
    public ResponseEntity<List<Pokemon>> findAll() {
        // A lógica de busca foi para o serviço. O controller apenas chama.
        List<Pokemon> pokemons = pokemonService.findAll();
        // Retornamos um status "200 OK" junto com a lista de pokémons.
        return ResponseEntity.ok(pokemonService.findAll());
    }

    // colocar end points aqui

}