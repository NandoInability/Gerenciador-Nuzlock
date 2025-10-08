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

    public String getEvolChain() {
        return evolChain;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrimaryType() {
        return primaryType;
    }

    public void setPrimaryType(String primaryType) {
        this.primaryType = primaryType;
    }

    public String getSecundaryType() {
        return secundaryType;
    }

    public void setSecundaryType(String secundaryType) {
        this.secundaryType = secundaryType;
    }

    public Integer getGeneration() {
        return generation;
    }

    public void setGeneration(Integer generation) {
        this.generation = generation;
    }

    public Integer getPkdexNumber() {
        return pkdexNumber;
    }

    public void setPkdexNumber(Integer pkdexNumber) {
        this.pkdexNumber = pkdexNumber;
    }

    public void setEvolChain(String evolChain) {
        this.evolChain = evolChain;
    }

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