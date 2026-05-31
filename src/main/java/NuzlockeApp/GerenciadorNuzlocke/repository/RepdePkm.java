package NuzlockeApp.GerenciadorNuzlocke.repository;

import NuzlockeApp.GerenciadorNuzlocke.DTO.DTO;
import NuzlockeApp.GerenciadorNuzlocke.entity.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepdePkm extends JpaRepository<Pokemon, Long> {

    @Query("SELECT new NuzlockeApp.GerenciadorNuzlocke.DTO.DTO(p.id, p.name, p.sprite, p.evolChain) FROM Pokemon p ORDER BY p.pkdexNumber ASC")
    List<DTO> findAllForPicker();

}