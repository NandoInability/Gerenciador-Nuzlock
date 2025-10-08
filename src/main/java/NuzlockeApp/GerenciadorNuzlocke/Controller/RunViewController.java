package NuzlockeApp.GerenciadorNuzlocke.Controller;
import NuzlockeApp.GerenciadorNuzlocke.entity.Pokemon;
import NuzlockeApp.GerenciadorNuzlocke.repository.RepPar;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import NuzlockeApp.GerenciadorNuzlocke.service.PKMCapturadoService;
import org.springframework.http.ResponseEntity;
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
    @Autowired
    private PKMCapturadoService pkmCapturadoService;
    @Autowired
    private RepPar repPar;

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

    @DeleteMapping("/{runId}/pares/{parId}/deletar")
    public String deletarParDaRun(@PathVariable Long runId, @PathVariable Long parId, RedirectAttributes redirectAttributes) { // Mude o retorno para String e adicione RedirectAttributes
        return repPar.findById(parId)
                .map(parLink -> {
                    // Opcional: Verifique se o ParLink realmente pertence à Run correta
                    // (Você já tem essa lógica comentada, pode descomentar e usar)
                    if (!parLink.getRun().getId().equals(runId)) {
                        redirectAttributes.addFlashAttribute("errorMessage", "ParLink não pertence a esta Run.");
                        return "redirect:/runs/" + runId;
                    }

                    repPar.delete(parLink);
                    redirectAttributes.addFlashAttribute("successMessage", "Par deletado com sucesso!"); // Mensagem de sucesso
                    return "redirect:/runs/" + runId; // <-- AQUI ESTÁ A MUDANÇA CHAVE! Redireciona para a página de detalhes da run
                })
                .orElseGet(() -> { // Use orElseGet para lambdas que retornam valores
                    redirectAttributes.addFlashAttribute("errorMessage", "Par não encontrado para exclusão."); // Mensagem de erro
                    return "redirect:/runs/" + runId; // Redireciona de volta para a página de detalhes da run, mesmo se não encontrou
                });
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

    @PostMapping("/{runId}/pares/capturas/{capturaId}/morrer")
    public String marcarPokemonComoMorto(
            @PathVariable Long runId,
            @PathVariable Long capturaId,
            RedirectAttributes redirectAttributes) {
        try {
            pkmCapturadoService.marcarMorto(capturaId);
            redirectAttributes.addFlashAttribute("successMessage", "Pokémon marcado como morto!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao marcar Pokémon como morto.");
            e.printStackTrace(); // Boa prática para ver erros no console
        }
        return "redirect:/runs/" + runId; // Redireciona de volta para a página de detalhes da run
    }

    @DeleteMapping("/{id}/deletar") // Novo mapeamento para deletar uma Run
    public String deletarRun(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            runsSLService.deleteById(id); // Assumindo que seu serviço tem um método deleteById
            redirectAttributes.addFlashAttribute("successMessage", "Run deletada com sucesso!");
            return "redirect:/"; // Redireciona para o dashboard (ou para a lista de todas as runs)
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao deletar a Run: " + e.getMessage());
            // Se houver erro, redireciona para a mesma página ou para uma página de erro
            return "redirect:/"; // Ou para /runs/{id} se quiser voltar à Run que falhou
        }
    }
}