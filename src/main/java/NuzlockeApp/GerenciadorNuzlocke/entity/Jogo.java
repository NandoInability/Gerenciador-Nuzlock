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
}
