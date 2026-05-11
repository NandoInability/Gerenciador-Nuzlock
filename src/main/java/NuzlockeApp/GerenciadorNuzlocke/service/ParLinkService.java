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
        PKMCapturado pkm1 = par.getPkm1();
        PKMCapturado pkm2 = par.getPkm2();
        PKMCapturado pkm3 = par.getPkm3();
        PKMCapturado pkm4 = par.getPkm4();
        RunsSL run = par.getRun();

        if (pkm1 == null || pkm1.getEspecie() == null ||
                pkm2 == null || pkm2.getEspecie() == null) {
            throw new IllegalArgumentException("Os Pokémon dos jogadores 1 e 2 são obrigatórios.");
        }

        if (run != null) {
            if (run.getJogador3() != null && !run.getJogador3().isBlank() && (pkm3 == null || pkm3.getEspecie() == null)) {
                throw new IllegalArgumentException("O Pokémon do jogador 3 (" + run.getJogador3() + ") é obrigatório.");
            }
            if (run.getJogador4() != null && !run.getJogador4().isBlank() && (pkm4 == null || pkm4.getEspecie() == null)) {
                throw new IllegalArgumentException("O Pokémon do jogador 4 (" + run.getJogador4() + ") é obrigatório.");
            }
        }

        // Coleta todas as espécies presentes no par para validação cruzada
        List<PKMCapturado> todos = new java.util.ArrayList<>();
        todos.add(pkm1); todos.add(pkm2);
        if (pkm3 != null && pkm3.getEspecie() != null) todos.add(pkm3);
        if (pkm4 != null && pkm4.getEspecie() != null) todos.add(pkm4);

        for (int i = 0; i < todos.size(); i++) {
            for (int j = i + 1; j < todos.size(); j++) {
                Pokemon e1 = todos.get(i).getEspecie();
                Pokemon e2 = todos.get(j).getEspecie();
                if (Objects.equals(e1.getId(), e2.getId())) {
                    throw new IllegalArgumentException("Dois Pokémon no par não podem ser da mesma espécie.");
                }
                if (e1.getEvolChain() != null && Objects.equals(e1.getEvolChain(), e2.getEvolChain())) {
                    throw new IllegalArgumentException("Dois Pokémon no par não podem ser da mesma família evolutiva.");
                }
            }
        }

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
