package NuzlockeApp.GerenciadorNuzlocke.repository;

import NuzlockeApp.GerenciadorNuzlocke.entity.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepdePkm extends JpaRepository<Pokemon, Long> {

}
