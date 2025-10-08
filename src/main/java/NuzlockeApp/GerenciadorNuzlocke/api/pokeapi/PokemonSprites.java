package NuzlockeApp.GerenciadorNuzlocke.api.pokeapi;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PokemonSprites {
    @JsonProperty("front_default") // Mapeia "front_default" do JSON para spriteUrl
    private String frontDefault;

    public String getFrontDefault() { return frontDefault; }
    public void setFrontDefault(String frontDefault) { this.frontDefault = frontDefault; }
}
