package NuzlockeApp.GerenciadorNuzlocke.Controller;
import NuzlockeApp.GerenciadorNuzlocke.entity.Pokemon;
import NuzlockeApp.GerenciadorNuzlocke.service.PokemonService;
import org.springframework.web.bind.annotation.*;
import NuzlockeApp.GerenciadorNuzlocke.entity.RunsSL;
import NuzlockeApp.GerenciadorNuzlocke.service.RunsSLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import NuzlockeApp.GerenciadorNuzlocke.entity.Jogo;
import NuzlockeApp.GerenciadorNuzlocke.service.JogoService;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
@RequestMapping("/runs") // Define /runs como URL base para este controller
public class RunViewController {

    @Autowired
    private RunsSLService runsSLService;
    @Autowired
    private JogoService jogoService;
    @Autowired
    private PokemonService pokemonService;

    // Método para mostrar o formulário (GET /runs/novo)
    @GetMapping("/novo")
    public String mostrarFormularioDeNovaRun(Model model) {
        List<Jogo> todosOsJogos = jogoService.findAll();
        List<Pokemon> todosOsPokemons = pokemonService.findAll();
        model.addAttribute("pokemons", todosOsPokemons);
        model.addAttribute("run", new RunsSL());
        model.addAttribute("jogos", todosOsJogos);
        model.addAttribute("run", new RunsSL());
        return "form-run";
    }

    // Método para salvar o formulário (POST /runs)
    @PostMapping
    public String salvarNovaRun(@ModelAttribute("run") RunsSL run, @RequestParam Long starterP1Id, @RequestParam Long starterP2Id) {
        runsSLService.criarNovaRunComIniciais(run, starterP1Id, starterP2Id);
        return "redirect:/"; // Redireciona para o dashboard
    }

    @GetMapping("/{id}")
    public String detalhesRun(@PathVariable Long id, Model model) {
        RunsSL runEncontrada = runsSLService.findById(id);
        if (runEncontrada != null) {
            model.addAttribute("run", runEncontrada);
            return "detalhes-run";
        }
        return "redirect:/";
    }

    @PostMapping("/{id}/deletar")
    public String deletarRun(@PathVariable Long id) {
        runsSLService.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/{id}/editar")
    public String mostrarFormDeEdicaoRun(@PathVariable Long id, Model model) {
        RunsSL runParaEditar = runsSLService.findById(id);
        List<Jogo> todosOsJogos = jogoService.findAll();

        model.addAttribute("jogos", todosOsJogos);
        model.addAttribute("run", runParaEditar);
        return "form-run";
    }

    @PostMapping("/{id}/editar")
    public String salvarEdicaoRun(@PathVariable Long id, @ModelAttribute("run") RunsSL runMod) {
        runMod.setId(id);
        runsSLService.save(runMod);
        return "redirect:/";
    }
}