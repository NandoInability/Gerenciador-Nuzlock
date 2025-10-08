package NuzlockeApp.GerenciadorNuzlocke.Controller;
import NuzlockeApp.GerenciadorNuzlocke.entity.RunsSL;
import NuzlockeApp.GerenciadorNuzlocke.service.RunsSLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;

@Controller // Mantenha como RestController
@RequestMapping("/api/runs") // Mantenha o prefixo /api para diferenciar da web
public class RunsSLController {
    @Autowired
    private RunsSLService runsSLService;

    // Endpoint para buscar todas as Runs (API)
    @GetMapping
    public ResponseEntity<List<RunsSL>> buscarTodasAsRuns() {
        List<RunsSL> runs = runsSLService.findAll();
        return ResponseEntity.ok(runs);
    }

    // Endpoint para buscar uma Run específica por ID (API)
    @GetMapping("/{id}")
    public ResponseEntity<RunsSL> buscarRunId(@PathVariable Long id) {
        RunsSL run = runsSLService.findById(id);
        if (run == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(run);
    }

    // Endpoint para criar uma Run (API)
    @PostMapping
    public ResponseEntity<RunsSL> criarRun(@RequestBody RunsSL run) {
        RunsSL runs = runsSLService.save(run); // Este save aqui não deve ter a lógica de iniciais!
        return new ResponseEntity<>(runs, HttpStatus.CREATED);
    }
}