package NuzlockeApp.GerenciadorNuzlocke.repository;
import NuzlockeApp.GerenciadorNuzlocke.entity.Jogo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JogoRepository extends JpaRepository<Jogo, Long> {
}
