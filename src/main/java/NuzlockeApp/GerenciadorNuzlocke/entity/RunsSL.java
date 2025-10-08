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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeDaRun() {
        return nomeDaRun;
    }

    public void setNomeDaRun(String nomeDaRun) {
        this.nomeDaRun = nomeDaRun;
    }

    public Jogo getJogo() {
        return jogo;
    }

    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }

    public String getJogador1() {
        return jogador1;
    }

    public void setJogador1(String jogador1) {
        this.jogador1 = jogador1;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJogador2() {
        return jogador2;
    }

    public void setJogador2(String jogador2) {
        this.jogador2 = jogador2;
    }

    public List<ParLink> getLinks() {
        return links;
    }

    public void setLinks(List<ParLink> links) {
        this.links = links;
    }
}
