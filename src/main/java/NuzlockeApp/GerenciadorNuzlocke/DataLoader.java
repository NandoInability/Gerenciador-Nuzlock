package NuzlockeApp.GerenciadorNuzlocke;

import NuzlockeApp.GerenciadorNuzlocke.entity.Pokemon;
import NuzlockeApp.GerenciadorNuzlocke.repository.RepdePkm; // Verifique se o import está correto
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private RepdePkm repository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Banco de dados vazio. Carregando dados...");
        if (repository.count() == 0){
            Pokemon p1 = new Pokemon();
            p1.setName("Bulbasaur");
            p1.setPrimaryType("Grass");
            p1.setSecundaryType("Poison");
            p1.setPkdexNumber(1);
            p1.setGeneration(1);
            //p1.setSprite("url_do_sprite_aqui");

            Pokemon p2 = new Pokemon();
            p2.setName("Charmander");
            p2.setPrimaryType("Fire");
            p2.setPkdexNumber(4);
            p2.setGeneration(1);

            Pokemon p3 = new Pokemon();
            p3.setName("Squirtle");
            p3.setPrimaryType("Water");
            p3.setPkdexNumber(7);
            p3.setGeneration(1);

            repository.save(p1);
            repository.save(p2);
            repository.save(p3);

            System.out.println("Dados carregados com sucesso!");
        } else  {
            System.out.println("O banco ja ta feito");
        }
    }
}
