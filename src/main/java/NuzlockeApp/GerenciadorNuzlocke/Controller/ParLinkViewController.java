package NuzlockeApp.GerenciadorNuzlocke.Controller;

import NuzlockeApp.GerenciadorNuzlocke.entity.ParLink;
import NuzlockeApp.GerenciadorNuzlocke.entity.Pokemon;
import NuzlockeApp.GerenciadorNuzlocke.entity.PKMCapturado;
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
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Set;
import NuzlockeApp.GerenciadorNuzlocke.DTO.DTO;

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
        RunsSL runAtual = runsSLService.findById(runId);

        Set<String> familiasJaCapturadas = new HashSet<>();
        if (runAtual != null && runAtual.getLinks() != null) {
            runAtual.getLinks().forEach(par -> {
                if (par.getPkm1() != null && par.getPkm1().getEspecie().getEvolChain() != null)
                    familiasJaCapturadas.add(par.getPkm1().getEspecie().getEvolChain());
                if (par.getPkm2() != null && par.getPkm2().getEspecie().getEvolChain() != null)
                    familiasJaCapturadas.add(par.getPkm2().getEspecie().getEvolChain());
                if (par.getPkm3() != null && par.getPkm3().getEspecie() != null && par.getPkm3().getEspecie().getEvolChain() != null)
                    familiasJaCapturadas.add(par.getPkm3().getEspecie().getEvolChain());
                if (par.getPkm4() != null && par.getPkm4().getEspecie() != null && par.getPkm4().getEspecie().getEvolChain() != null)
                    familiasJaCapturadas.add(par.getPkm4().getEspecie().getEvolChain());
            });
        }

        List<DTO> pokemonsDisponiveis = pokemonService.findAllForPicker().stream()
                .filter(p -> p.getEvolChain() != null && !familiasJaCapturadas.contains(p.getEvolChain()))
                .collect(Collectors.toList());

        ParLink parVazio = new ParLink();
        if (runAtual != null) {
            parVazio.setPkm1(new PKMCapturado());
            parVazio.setPkm2(new PKMCapturado());
            if (runAtual.getJogador3() != null && !runAtual.getJogador3().isBlank()) parVazio.setPkm3(new PKMCapturado());
            if (runAtual.getJogador4() != null && !runAtual.getJogador4().isBlank()) parVazio.setPkm4(new PKMCapturado());
        }

        model.addAttribute("runId", runId);
        model.addAttribute("run", runAtual);
        model.addAttribute("par", parVazio);
        model.addAttribute("pokemons", pokemonsDisponiveis);
        return "form-par";
    }

    @PostMapping
    public String salvarNovoPar(@PathVariable Long runId, @ModelAttribute("par") ParLink par, Model model) {
        RunsSL run = runsSLService.findById(runId);
        if (run != null) par.setRun(run);

        // Se pkm3/pkm4 vieram sem espécie, zera para não salvar vazio
        if (par.getPkm3() != null && (par.getPkm3().getEspecie() == null || par.getPkm3().getEspecie().getId() == null)) par.setPkm3(null);
        if (par.getPkm4() != null && (par.getPkm4().getEspecie() == null || par.getPkm4().getEspecie().getId() == null)) par.setPkm4(null);

        try {
            parLinkService.save(par);
            return "redirect:/runs/" + runId;
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("runId", runId);
            model.addAttribute("run", run);
            model.addAttribute("par", par);
            model.addAttribute("pokemons", pokemonService.findAllForPicker());
            return "form-par";
        }
    }

    @PostMapping("/{parId}/editar")
    public String salvarEdicao(@PathVariable Long runId, @PathVariable Long parId,
                               @ModelAttribute("par") ParLink parAtualizado, Model model) {
        RunsSL run = runsSLService.findById(runId);
        if (run != null) parAtualizado.setRun(run);
        parAtualizado.setId(parId);

        if (parAtualizado.getPkm3() != null && (parAtualizado.getPkm3().getEspecie() == null || parAtualizado.getPkm3().getEspecie().getId() == null)) parAtualizado.setPkm3(null);
        if (parAtualizado.getPkm4() != null && (parAtualizado.getPkm4().getEspecie() == null || parAtualizado.getPkm4().getEspecie().getId() == null)) parAtualizado.setPkm4(null);

        try {
            parLinkService.save(parAtualizado);
            return "redirect:/runs/" + runId;
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("runId", runId);
            model.addAttribute("par", parAtualizado);
            model.addAttribute("pokemons", pokemonService.findAllForPicker());
            return "form-par";
        }
    }

    // Adiciona endpoints de evolução para pkm1, pkm2, pkm3 e pkm4
    @PostMapping("/{parId}/pkm1/evoluir")
    public String evoluirPkm1(@PathVariable Long runId, @PathVariable Long parId,
                              @RequestParam Long novaEspecieId) {
        ParLink par = parLinkService.findById(parId);
        par.getPkm1().setEspecie(pokemonService.findById(novaEspecieId));
        pkmCapturadoService.save(par.getPkm1());
        return "redirect:/runs/" + runId;
    }

    @PostMapping("/{parId}/pkm2/evoluir")
    public String evoluirPkm2(@PathVariable Long runId, @PathVariable Long parId,
                              @RequestParam Long novaEspecieId) {
        ParLink par = parLinkService.findById(parId);
        par.getPkm2().setEspecie(pokemonService.findById(novaEspecieId));
        pkmCapturadoService.save(par.getPkm2());
        return "redirect:/runs/" + runId;
    }

    @PostMapping("/{parId}/pkm3/evoluir")
    public String evoluirPkm3(@PathVariable Long runId, @PathVariable Long parId,
                              @RequestParam Long novaEspecieId) {
        ParLink par = parLinkService.findById(parId);
        par.getPkm3().setEspecie(pokemonService.findById(novaEspecieId));
        pkmCapturadoService.save(par.getPkm3());
        return "redirect:/runs/" + runId;
    }

    @PostMapping("/{parId}/pkm4/evoluir")
    public String evoluirPkm4(@PathVariable Long runId, @PathVariable Long parId,
                              @RequestParam Long novaEspecieId) {
        ParLink par = parLinkService.findById(parId);
        par.getPkm4().setEspecie(pokemonService.findById(novaEspecieId));
        pkmCapturadoService.save(par.getPkm4());
        return "redirect:/runs/" + runId;
    }

}
