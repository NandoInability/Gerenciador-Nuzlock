package NuzlockeApp.GerenciadorNuzlocke.Controller;
import NuzlockeApp.GerenciadorNuzlocke.entity.ParLink;
import NuzlockeApp.GerenciadorNuzlocke.service.ParLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/ParLink")
public class ParLinkController {
    @Autowired
    private ParLinkService parLinkService;

    @GetMapping
    public ResponseEntity<List<ParLink>> findAll() {
        List<ParLink> parLinks = parLinkService.findAll();
        return ResponseEntity.ok(parLinks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParLink> findById(@PathVariable Long id) {
        ParLink parLink = parLinkService.findById(id);
        if (parLink == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(parLink);
    }

    @PostMapping
    public ResponseEntity<ParLink> criarParLink(@RequestBody ParLink parLink) {
        ParLink par = parLinkService.save(parLink);
        return new ResponseEntity<>(par, HttpStatus.CREATED);
    }
}
