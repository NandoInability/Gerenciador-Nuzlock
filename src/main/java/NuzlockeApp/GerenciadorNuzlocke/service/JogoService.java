package NuzlockeApp.GerenciadorNuzlocke.service;
import NuzlockeApp.GerenciadorNuzlocke.entity.Jogo;
import NuzlockeApp.GerenciadorNuzlocke.repository.JogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class JogoService {
    @Autowired
    private  JogoRepository jogoRepository;

    public List<Jogo> findAll() {
        return jogoRepository.findAll();
    }
}
