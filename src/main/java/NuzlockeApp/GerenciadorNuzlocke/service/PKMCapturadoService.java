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
                    // Marca todos os parceiros como mortos
                    marcarParceiroMorto(par.getPkm1(), capturaPrincipal);
                    marcarParceiroMorto(par.getPkm2(), capturaPrincipal);
                    marcarParceiroMorto(par.getPkm3(), capturaPrincipal);
                    marcarParceiroMorto(par.getPkm4(), capturaPrincipal);
                }
            }
        }
    }

    private void marcarParceiroMorto(PKMCapturado parceiro, PKMCapturado principal) {
        if (parceiro != null && !parceiro.getId().equals(principal.getId()) && "Vivo".equals(parceiro.getStatus())) {
            parceiro.setStatus("Morto");
            rep.save(parceiro);
        }
    }
    public PKMCapturado save(PKMCapturado pkmCapturado) {
        return rep.save(pkmCapturado);
    }
}
