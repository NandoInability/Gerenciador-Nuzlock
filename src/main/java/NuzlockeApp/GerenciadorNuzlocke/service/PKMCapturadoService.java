package NuzlockeApp.GerenciadorNuzlocke.service;
import NuzlockeApp.GerenciadorNuzlocke.entity.PKMCapturado;
import NuzlockeApp.GerenciadorNuzlocke.entity.ParLink;
import NuzlockeApp.GerenciadorNuzlocke.repository.RepPar;
import NuzlockeApp.GerenciadorNuzlocke.repository.RepPKMCapturado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
@Service
public class PKMCapturadoService {
    @Autowired
    private RepPKMCapturado rep;
    @Autowired
    private RepPar repPar;
    public PKMCapturado findById(Long id) {
        return rep.findById(id).orElse(null);
    }

    public void marcarMorto(Long capturaId) {
        Optional<PKMCapturado> capturaOpt = rep.findById(capturaId);

        if (capturaOpt.isPresent()) {
            PKMCapturado capturaPrincipal = capturaOpt.get();

            if ("Vivo".equals(capturaPrincipal.getStatus())) {
                capturaPrincipal.setStatus("Morto");
                rep.save(capturaPrincipal);

                Optional<ParLink> parOpt = repPar.findByCapturaId(capturaId);

                if (parOpt.isPresent()) {
                    ParLink par = parOpt.get();

                    PKMCapturado capturaParceira = null;
                    if (par.getPkm1().getId().equals(capturaPrincipal.getId())) {
                        capturaParceira = par.getPkm2();
                    } else {
                        capturaParceira = par.getPkm1();
                    }

                    if (capturaParceira != null && "Vivo".equals(capturaParceira.getStatus())) {
                        capturaParceira.setStatus("Morto");
                        rep.save(capturaParceira);
                }
            }
        }
    }
}
    public PKMCapturado save(PKMCapturado pkmCapturado) {
        return rep.save(pkmCapturado);
    }
}
