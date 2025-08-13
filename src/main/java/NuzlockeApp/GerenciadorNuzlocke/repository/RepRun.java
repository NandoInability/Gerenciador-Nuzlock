package NuzlockeApp.GerenciadorNuzlocke.repository;

import NuzlockeApp.GerenciadorNuzlocke.entity.RunsSL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface RepRun  extends JpaRepository<RunsSL, Long> {
}
