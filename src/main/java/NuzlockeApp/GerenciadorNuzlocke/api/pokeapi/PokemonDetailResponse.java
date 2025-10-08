package NuzlockeApp.GerenciadorNuzlocke.api.pokeapi;
import java.util.List;

public class PokemonDetailResponse {
    private int id;
    private String name;
    private int height;
    private int weight;
    private List<PokemonType> types;
    private PokemonSprites sprites;
    private PokemonSpecies species;

    public PokemonSprites getSprites() {
        return sprites;
    }
    public void setSprites(PokemonSprites sprites) {
        this.sprites = sprites;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
    public List<PokemonType> getTypes() {
        return types;
    }
    public void setTypes(List<PokemonType> types) {
        this.types = types;
    }
    public PokemonSpecies getSpecies() { return species; }
    public void setSpecies(PokemonSpecies species) { this.species = species; }
}


