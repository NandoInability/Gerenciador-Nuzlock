package NuzlockeApp.GerenciadorNuzlocke.Controller;

import NuzlockeApp.GerenciadorNuzlocke.DTO.DTO;
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
    public ResponseEntity<List<DTO>> findAll() {
        return ResponseEntity.ok(pokemonService.findAllForPicker());
    }

    // colocar end points aqui

}