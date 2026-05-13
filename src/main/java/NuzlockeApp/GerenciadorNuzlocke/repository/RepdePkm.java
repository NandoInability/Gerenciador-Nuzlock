package NuzlockeApp.GerenciadorNuzlocke.repository;

import NuzlockeApp.GerenciadorNuzlocke.entity.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepdePkm extends JpaRepository<Pokemon, Long> {

    List<Pokemon> findAllByOrderByPkdexNumberAsc();

}