package NuzlockeApp.GerenciadorNuzlocke.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "pares_link")
@Data
public class ParLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "run_id")
    private RunsSL run;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "captura_jogador1_id")
    private PKMCapturado pkm1;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "captura_jogador2_id")
    private PKMCapturado pkm2;

    @Column(name = "local_captura")
    private String localCaptura;



}
