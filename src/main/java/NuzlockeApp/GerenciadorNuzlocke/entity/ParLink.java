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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RunsSL getRun() {
        return run;
    }

    public void setRun(RunsSL run) {
        this.run = run;
    }

    public PKMCapturado getPkm1() {
        return pkm1;
    }

    public void setPkm1(PKMCapturado pkm1) {
        this.pkm1 = pkm1;
    }

    public PKMCapturado getPkm2() {
        return pkm2;
    }

    public void setPkm2(PKMCapturado pkm2) {
        this.pkm2 = pkm2;
    }

    public String getLocalCaptura() {
        return localCaptura;
    }

    public void setLocalCaptura(String localCaptura) {
        this.localCaptura = localCaptura;
    }

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "captura_jogador1_id")
    private PKMCapturado pkm1;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "captura_jogador2_id")
    private PKMCapturado pkm2;

    @Column(name = "local_captura")
    private String localCaptura;



}
