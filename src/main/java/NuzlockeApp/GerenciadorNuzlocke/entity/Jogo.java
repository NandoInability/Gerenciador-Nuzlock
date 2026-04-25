package NuzlockeApp.GerenciadorNuzlocke.entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Jogo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private int geracao;
    private String sprite;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getGeracao() {
        return geracao;
    }

    public void setGeracao(int geracao) {
        this.geracao = geracao;
    }

    public String getSprite() { return sprite; }

    public void setSprite(String sprite) { this.sprite = sprite; }
}
