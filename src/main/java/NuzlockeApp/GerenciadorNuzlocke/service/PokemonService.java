package NuzlockeApp.GerenciadorNuzlocke.service;
import NuzlockeApp.GerenciadorNuzlocke.DTO.DTO;
import NuzlockeApp.GerenciadorNuzlocke.entity.Pokemon;
import NuzlockeApp.GerenciadorNuzlocke.repository.RepdePkm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PokemonService {
    @Autowired
    private RepdePkm pokemonRepository;

    public Pokemon save(Pokemon pokemon) {
        return pokemonRepository.save(pokemon);
    }

    public List<DTO> findAllForPicker() {
        return pokemonRepository.findAllForPicker();
    }

    public List<Pokemon> findAll() {
        return pokemonRepository.findAllByOrderByPkdexNumberAsc();
    }

    public Pokemon findById(Long id) {
        return pokemonRepository.findById(id).orElse(null);
    }
}
