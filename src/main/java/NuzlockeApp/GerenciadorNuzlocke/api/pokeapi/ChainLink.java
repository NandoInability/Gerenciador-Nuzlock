package NuzlockeApp.GerenciadorNuzlocke.api.pokeapi;

import java.util.List;

public class ChainLink {
    private Species species;
    private List<ChainLink> evolves_to;

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    public List<ChainLink> getEvolves_to() {
        return evolves_to;
    }

    public void setEvolves_to(List<ChainLink> evolves_to) {
        this.evolves_to = evolves_to;
    }
}
