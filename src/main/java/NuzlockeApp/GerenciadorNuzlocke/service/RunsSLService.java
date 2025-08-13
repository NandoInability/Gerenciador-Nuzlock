package NuzlockeApp.GerenciadorNuzlocke.service;
import NuzlockeApp.GerenciadorNuzlocke.entity.Pokemon;
import NuzlockeApp.GerenciadorNuzlocke.entity.RunsSL;
import NuzlockeApp.GerenciadorNuzlocke.repository.RepRun;
import NuzlockeApp.GerenciadorNuzlocke.repository.RepdePkm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RunsSLService {
    @Autowired
    private RepRun runsRepository;

    public RunsSL save(RunsSL run) {
        return runsRepository.save(run);
    }

    public List<RunsSL> findAll() {
        return runsRepository.findAll();
    }

    public RunsSL findById(Long id) {
        return runsRepository.findById(id).orElse(null);
    }
}
