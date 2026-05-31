package NuzlockeApp.GerenciadorNuzlocke.repository;

import NuzlockeApp.GerenciadorNuzlocke.entity.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepdePkm extends JpaRepository<Pokemon, Long> {

    List<Pokemon> findAllByOrderByPkdexNumberAsc();

    // Este é o método que acaba com a lentidão e o erro 500!
    // Ele busca o Pokémon e as evoluções dele em UMA única consulta ao banco.
    @Query("SELECT p FROM Pokemon p LEFT JOIN FETCH p.nextEvolutions WHERE p.pkdexNumber = :pkdexNumber")
    Optional<Pokemon> findByIdComEvolucoes(@Param("pkdexNumber") Integer pkdexNumber);

}