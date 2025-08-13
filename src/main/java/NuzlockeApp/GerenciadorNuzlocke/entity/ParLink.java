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

    @ManyToOne
    @JoinColumn(name = "pkm1_id")
    private Pokemon pkm1;

    @ManyToOne
    @JoinColumn(name = "pkm2_id")
    private Pokemon pkm2;

    @Column(name = "local_captura")
    private String localCaptura;



}
