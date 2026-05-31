package NuzlockeApp.GerenciadorNuzlocke.entity;

import lombok.ToString;
import lombok.EqualsAndHashCode;
import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pokemons")
@Data // O Lombok gera todos os Getters e Setters automaticamente para você!
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

    @Column(name = "evol_chain")
    private String evolChain;

    // Status Base
    @Column(name = "hp")
    private Integer hp;

    @Column(name = "attack")
    private Integer attack;

    @Column(name = "defense")
    private Integer defense;

    @Column(name = "sp_attack")
    private Integer spAttack;

    @Column(name = "sp_defense")
    private Integer spDefense;

    @Column(name = "speed")
    private Integer speed;


    @Column(name = "evo_details")
    private String evoDetails;

    @ManyToMany
    @JoinTable(
            name = "pokemon_evolutions",
            joinColumns = @JoinColumn(name = "pokemon_id"),
            inverseJoinColumns = @JoinColumn(name = "next_evolution_id")
    )
    private List<Pokemon> nextEvolutions = new ArrayList<>();
}