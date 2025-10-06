package NuzlockeApp.GerenciadorNuzlocke.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "runs")
@Data
public class RunsSL {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_jogo")
    private String nomeDaRun;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "jogo_id")
    private Jogo jogo;

    @Column(name = "status")
    private String status = "Em andamento";

    private String jogador1;
    private String jogador2;

    @OneToMany(mappedBy = "run", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ParLink> links;
}
