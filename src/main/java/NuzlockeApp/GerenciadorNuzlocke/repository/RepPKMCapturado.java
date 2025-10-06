package NuzlockeApp.GerenciadorNuzlocke.repository;
import NuzlockeApp.GerenciadorNuzlocke.entity.PKMCapturado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepPKMCapturado extends JpaRepository<PKMCapturado, Long> {
}
