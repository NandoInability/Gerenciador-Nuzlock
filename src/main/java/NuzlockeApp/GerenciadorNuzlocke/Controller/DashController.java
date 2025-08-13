package NuzlockeApp.GerenciadorNuzlocke.Controller;
import NuzlockeApp.GerenciadorNuzlocke.entity.RunsSL;
import NuzlockeApp.GerenciadorNuzlocke.service.RunsSLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashController {
    @Autowired
    private RunsSLService runsSLController;
    @GetMapping("/")
    public String dash(Model model) {
        List<RunsSL> todasAsRuns = runsSLController.findAll();
        model.addAttribute("runs", todasAsRuns);
        return "dashboard";
    }
}
