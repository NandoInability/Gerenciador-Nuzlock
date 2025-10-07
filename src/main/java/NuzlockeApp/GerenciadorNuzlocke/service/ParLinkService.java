package NuzlockeApp.GerenciadorNuzlocke.service;
import NuzlockeApp.GerenciadorNuzlocke.entity.PKMCapturado;
import NuzlockeApp.GerenciadorNuzlocke.entity.Pokemon;
import NuzlockeApp.GerenciadorNuzlocke.entity.RunsSL;
import NuzlockeApp.GerenciadorNuzlocke.entity.ParLink;
import NuzlockeApp.GerenciadorNuzlocke.repository.RepPar;
import NuzlockeApp.GerenciadorNuzlocke.repository.RepRun;
import NuzlockeApp.GerenciadorNuzlocke.repository.RepdePkm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Service
public class ParLinkService {
    @Autowired
    private RepPar parLinkRepository;

    public ParLink save(ParLink par) {
        // Lógica futura: verificar se os pokémons do par são válidos, etc.
        PKMCapturado pkmCapturado1 = par.getPkm1();
        PKMCapturado pkmCapturado2 = par.getPkm2();
        if (pkmCapturado1 == null || pkmCapturado1.getEspecie() == null ||
                pkmCapturado2 == null || pkmCapturado2.getEspecie() == null) {
            throw new IllegalArgumentException("Ambos os Pokémon do par devem ser válidos e ter uma espécie definida.");
        }

        Pokemon especie1 = pkmCapturado1.getEspecie();
        Pokemon especie2 = pkmCapturado2.getEspecie();

        // 2. Validação: Não podem ser o mesmo Pokémon (espécie)
        if (Objects.equals(especie1.getId(), especie2.getId())) {
            throw new IllegalArgumentException("Os Pokémon no par não podem ser da mesma espécie.");
        }
        // Ou, se você comparar pelo número da Pokedex:
        // if (Objects.equals(especie1.getPkdexNumber(), especie2.getPkdexNumber())) {
        //     throw new IllegalArgumentException("Os Pokémon no par não podem ser da mesma espécie (Pokedex Number).");
        // }


        // 3. Validação: Não podem ser da mesma família evolutiva
        if (especie1.getEvolChain() != null && especie2.getEvolChain() != null &&
                Objects.equals(especie1.getEvolChain(), especie2.getEvolChain())) {
            throw new IllegalArgumentException("Os Pokémon no par não podem ser da mesma família evolutiva.");
        }

        // Lógica futura: verificar se os pokémons do par são válidos, etc.
        return parLinkRepository.save(par);
    }

    public List<ParLink> findAll() {
        return parLinkRepository.findAll();
    }

    public ParLink findById(Long id) {
        return parLinkRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        parLinkRepository.deleteById(id);
    }
}
