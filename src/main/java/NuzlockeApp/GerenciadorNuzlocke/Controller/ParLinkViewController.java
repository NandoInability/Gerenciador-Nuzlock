package NuzlockeApp.GerenciadorNuzlocke.Controller;

import NuzlockeApp.GerenciadorNuzlocke.entity.ParLink;
import NuzlockeApp.GerenciadorNuzlocke.entity.Pokemon;
import NuzlockeApp.GerenciadorNuzlocke.entity.PKMCapturado; // Importe a nova entidade
import NuzlockeApp.GerenciadorNuzlocke.entity.RunsSL;
import NuzlockeApp.GerenciadorNuzlocke.service.PKMCapturadoService;
import NuzlockeApp.GerenciadorNuzlocke.service.ParLinkService;
import NuzlockeApp.GerenciadorNuzlocke.service.PokemonService;
import NuzlockeApp.GerenciadorNuzlocke.service.RunsSLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.HashSet; // Garanta que estes imports existam
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors; // Adicione este import
import java.util.Set; // Adicione este import

@Controller
@RequestMapping("/runs/{runId}/pares") // A URL base será relativa a uma run
public class ParLinkViewController {

    @Autowired
    private ParLinkService parLinkService;

    @Autowired
    private PokemonService pokemonService;

    @Autowired
    private RunsSLService runsSLService; // Precisamos do serviço de Run
    @Autowired
    private PKMCapturadoService pkmCapturadoService;

    // Método para MOSTRAR o formulário de novo par
    @GetMapping("/novo")
    public String mostrarFormularioDeNovoPar(@PathVariable Long runId, Model model) {
        // 1. Busca a Run atual.
        RunsSL runAtual = runsSLService.findById(runId);

        // 2. LÓGICA CORRETA: Coleta as famílias de evolução JÁ CAPTURADAS (tanto do pkm1 quanto do pkm2).
        Set<String> familiasJaCapturadas = new HashSet<>();
        if (runAtual != null && runAtual.getLinks() != null) {
            // Coleta as chains do pkm1, ignorando qualquer valor nulo
            runAtual.getLinks().stream()
                    .map(par -> par.getPkm1().getEspecie().getEvolChain())
                    .filter(Objects::nonNull)
                    .forEach(familiasJaCapturadas::add);

            // Coleta as chains do pkm2, ignorando qualquer valor nulo
            runAtual.getLinks().stream()
                    .map(par -> par.getPkm2().getEspecie().getEvolChain())
                    .filter(Objects::nonNull)
                    .forEach(familiasJaCapturadas::add);
        }

        // 3. Busca TODOS os Pokémon do Pokedex.
        List<Pokemon> todosOsPokemons = pokemonService.findAll();

        // 4. Filtra a lista, criando uma nova APENAS com os Pokémon disponíveis.
        List<Pokemon> pokemonsDisponiveis = todosOsPokemons.stream()
                .filter(pokemon -> pokemon.getEvolChain() != null && !familiasJaCapturadas.contains(pokemon.getEvolChain()))
                .collect(Collectors.toList());

        // 5. Prepara o objeto ParLink vazio para o formulário.
        ParLink parVazio = new ParLink();
        parVazio.setPkm1(new PKMCapturado());
        parVazio.setPkm2(new PKMCapturado());

        // 6. Envia os dados corretos para o HTML.
        model.addAttribute("runId", runId);
        model.addAttribute("par", parVazio);
        // IMPORTANTE: Enviando a lista JÁ FILTRADA para o HTML.
        model.addAttribute("pokemons", pokemonsDisponiveis);

        return "form-par";
    }
    // Método para SALVAR o formulário de novo par
    @PostMapping
    public String salvarNovoPar(@PathVariable Long runId, @ModelAttribute("par") ParLink par, Model model) {
        RunsSL run = runsSLService.findById(runId);
        if (run != null){
            par.setRun(run);
        }

        try {
            parLinkService.save(par);
            return "redirect:/runs/" + runId; // Redireciona para a página de detalhes da run em caso de sucesso
        } catch (IllegalArgumentException e) {
            // Se houver um erro de validação, adicione a mensagem ao modelo
            model.addAttribute("errorMessage", e.getMessage());
            // Re-popule o modelo com os dados necessários para exibir o formulário novamente
            // Isso inclui a runId, o objeto 'par' (com os dados que o usuário tentou enviar)
            // e a lista de pokemons (filtrada, como no método GET)

            // Recriar a lista de pokemons disponíveis para o formulário
            Set<String> familiasJaCapturadas = new HashSet<>();
            if (run != null && run.getLinks() != null) {
                run.getLinks().stream()
                        .map(p -> p.getPkm1().getEspecie().getEvolChain())
                        .filter(Objects::nonNull)
                        .forEach(familiasJaCapturadas::add);
                run.getLinks().stream()
                        .map(p -> p.getPkm2().getEspecie().getEvolChain())
                        .filter(Objects::nonNull)
                        .forEach(familiasJaCapturadas::add);
            }
            List<Pokemon> todosOsPokemons = pokemonService.findAll();
            List<Pokemon> pokemonsDisponiveis = todosOsPokemons.stream()
                    .filter(pokemon -> pokemon.getEvolChain() != null && !familiasJaCapturadas.contains(pokemon.getEvolChain()))
                    .collect(Collectors.toList());

            model.addAttribute("runId", runId); // Garante que o runId ainda esteja no modelo
            model.addAttribute("par", par); // Mantém os dados que o usuário digitou
            model.addAttribute("pokemons", pokemonsDisponiveis); // Recarrega os pokemons disponíveis
            return "form-par"; // Retorna para o formulário com a mensagem de erro
        }
    }
}
