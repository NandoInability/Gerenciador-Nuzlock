package NuzlockeApp.GerenciadorNuzlocke.service;
import NuzlockeApp.GerenciadorNuzlocke.entity.Pokemon;
import NuzlockeApp.GerenciadorNuzlocke.entity.RunsSL;
import NuzlockeApp.GerenciadorNuzlocke.repository.RepRun;
import NuzlockeApp.GerenciadorNuzlocke.repository.RepdePkm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import NuzlockeApp.GerenciadorNuzlocke.entity.PKMCapturado;
import NuzlockeApp.GerenciadorNuzlocke.entity.ParLink;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class RunsSLService {
    @Autowired
    private RepRun runsRepository;
    @Autowired
    private PokemonService pokemonService;
    @Autowired
    private ParLinkService parLinkService;
    @Autowired
    private PKMCapturadoService pkmCapturadoService;

    public RunsSL save(RunsSL run) {
        return runsRepository.save(run);
    }

    public List<RunsSL> findAll() {
        return runsRepository.findAll();
    }

    public RunsSL findById(Long id) {
        return runsRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        RunsSL run = runsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Run não encontrada com ID: " + id));
        runsRepository.deleteById(id);
    }

    @Transactional
    public void criarNovaRunComIniciais(RunsSL run, Long starterP1Id, Long starterP2Id) {
        RunsSL runSalva = runsRepository.save(run); // Salva a run primeiro para ter um ID

        Pokemon especieP1 = pokemonService.findById(starterP1Id);
        Pokemon especieP2 = pokemonService.findById(starterP2Id);

        if (especieP1 == null || especieP2 == null) {
            throw new IllegalArgumentException("Pokémon inicial não encontrado.");
        }

        PKMCapturado capturadoP1 = new PKMCapturado();
        capturadoP1.setEspecie(especieP1);
        capturadoP1.setApelido(especieP1.getName());
        capturadoP1.setStatus("Vivo"); // Adicione o status inicial
        pkmCapturadoService.save(capturadoP1); // SALVA o PKMCapturado

        PKMCapturado capturadoP2 = new PKMCapturado();
        capturadoP2.setEspecie(especieP2);
        capturadoP2.setApelido(especieP2.getName());
        capturadoP2.setStatus("Vivo"); // Adicione o status inicial
        pkmCapturadoService.save(capturadoP2); // SALVA o PKMCapturado

        ParLink primeiroPar = new ParLink();
        primeiroPar.setRun(runSalva);
        primeiroPar.setLocalCaptura("Pokemon Inicial");
        primeiroPar.setPkm1(capturadoP1);
        primeiroPar.setPkm2(capturadoP2);
        parLinkService.save(primeiroPar); // SALVA o ParLink
    }
}
