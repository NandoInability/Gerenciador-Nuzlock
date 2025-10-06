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

    public RunsSL save(RunsSL run) {
        return runsRepository.save(run);
    }

    public List<RunsSL> findAll() {
        return runsRepository.findAll();
    }

    public RunsSL findById(Long id) {
        return runsRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) { runsRepository.deleteById(id); }

    @Transactional
    public void criarNovaRunComIniciais(RunsSL run, Long starterP1Id, Long starterP2Id) {
        RunsSL runSalva = runsRepository.save(run);
            Pokemon especieP1 = pokemonService.findById(starterP1Id);
            Pokemon especieP2 = pokemonService.findById(starterP2Id);

            PKMCapturado capturadoP1 = new PKMCapturado();
            capturadoP1.setEspecie(especieP1);
            capturadoP1.setApelido(especieP1.getName());

            PKMCapturado capturadoP2 = new PKMCapturado();
            capturadoP2.setEspecie(especieP2);
            capturadoP2.setApelido(especieP2.getName());

            ParLink primeiroPar = new ParLink();
            primeiroPar.setRun(runSalva);
            primeiroPar.setLocalCaptura("Inicial");
            primeiroPar.setPkm1(capturadoP1);
            primeiroPar.setPkm2(capturadoP2);
            parLinkService.save(primeiroPar);
    }
}
