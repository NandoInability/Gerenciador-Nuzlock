package NuzlockeApp.GerenciadorNuzlocke.entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class PKMCapturado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String apelido;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pokemon_especie_id")
    private Pokemon especie;
    private String status = "Vivo";

    public PKMCapturado(){

    }
}
