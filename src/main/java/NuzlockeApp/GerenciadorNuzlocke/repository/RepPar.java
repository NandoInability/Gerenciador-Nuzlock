package NuzlockeApp.GerenciadorNuzlocke.repository;

import NuzlockeApp.GerenciadorNuzlocke.entity.ParLink;
import NuzlockeApp.GerenciadorNuzlocke.entity.PKMCapturado;
import NuzlockeApp.GerenciadorNuzlocke.entity.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepPar extends JpaRepository<ParLink, Long> {
    @Query("SELECT p FROM ParLink p WHERE p.pkm1.id = :capturaId OR p.pkm2.id = :capturaId")
    Optional<ParLink> findByCapturaId(Long capturaId);
}
