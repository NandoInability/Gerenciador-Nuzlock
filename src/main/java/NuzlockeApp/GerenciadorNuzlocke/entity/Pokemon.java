package NuzlockeApp.GerenciadorNuzlocke.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "pokemons")
@Data
public class Pokemon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "primary_type")
    private String primaryType;
    
    @Column(name = "secundary_type")
    private String secundaryType;
    
    @Column(name = "generation")
    private Integer generation;
    
    @Column(name = "pkdex_number")
    private Integer pkdexNumber;
    
    @Column(name = "sprite")
    private String sprite;

    private String evolChain;

    public String getSprite() {
        return sprite;
    }
    public void setSprite(String sprite) {
        this.sprite = sprite;
    }
}