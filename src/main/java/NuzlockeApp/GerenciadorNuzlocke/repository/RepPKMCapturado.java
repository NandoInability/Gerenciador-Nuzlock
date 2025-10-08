package NuzlockeApp.GerenciadorNuzlocke.repository;
import NuzlockeApp.GerenciadorNuzlocke.entity.PKMCapturado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepPKMCapturado extends JpaRepository<PKMCapturado, Long> {
}
