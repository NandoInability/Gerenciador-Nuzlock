package NuzlockeApp.GerenciadorNuzlocke.Controller;
import NuzlockeApp.GerenciadorNuzlocke.entity.RunsSL;
import NuzlockeApp.GerenciadorNuzlocke.service.RunsSLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/runs")
public class RunsSLController {
    @Autowired
    private RunsSLService runsSLService;

    // Endpoint para buscar todas as Runs
    @GetMapping
    public ResponseEntity<List<RunsSL>> buscarTodasAsRuns() {
        List<RunsSL> runs = runsSLService.findAll();
        return ResponseEntity.ok(runs);
    }

    // Endpoint para buscar uma Run específica por ID
    @GetMapping("/{id}")
    public ResponseEntity<RunsSL> buscarRunId(@PathVariable Long id) {
        RunsSL run = runsSLService.findById(id);
        if (run == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(run);
    }

    @PostMapping
    public ResponseEntity<RunsSL> criarRun(@RequestBody RunsSL run) {
        // Dados Login
        RunsSL runs = runsSLService.save(run);
        return new ResponseEntity<>(runs, HttpStatus.CREATED);
    }
}
