package NuzlockeApp.GerenciadorNuzlocke.DTO;

public class DTO {
    private Long id;
    private String name;
    private String sprite;
    private String evolChain;

    public DTO(Long id, String name, String sprite, String evolChain) {
        this.id = id;
        this.name = name;
        this.sprite = sprite;
        this.evolChain = evolChain;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getSprite() { return sprite; }
    public String getEvolChain() { return evolChain; }
}