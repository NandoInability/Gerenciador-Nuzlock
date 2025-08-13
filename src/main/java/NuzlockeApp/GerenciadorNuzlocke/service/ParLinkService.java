package NuzlockeApp.GerenciadorNuzlocke.service;
import NuzlockeApp.GerenciadorNuzlocke.entity.Pokemon;
import NuzlockeApp.GerenciadorNuzlocke.entity.RunsSL;
import NuzlockeApp.GerenciadorNuzlocke.entity.ParLink;
import NuzlockeApp.GerenciadorNuzlocke.repository.RepPar;
import NuzlockeApp.GerenciadorNuzlocke.repository.RepRun;
import NuzlockeApp.GerenciadorNuzlocke.repository.RepdePkm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ParLinkService {
    @Autowired
    private RepPar parLinkRepository;

    public ParLink save(ParLink par) {
        // Lógica futura: verificar se os pokémons do par são válidos, etc.
        return parLinkRepository.save(par);
    }

    public List<ParLink> findAll() {
        return parLinkRepository.findAll();
    }

    public ParLink findById(Long id) {
        return parLinkRepository.findById(id).orElse(null);
    }
}
