package br.com.alertacidadao.alerta_cidadao.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alertacidadao.alerta_cidadao.dto.OrientacaoIAResponseDTO;
import br.com.alertacidadao.alerta_cidadao.service.AssistenteIAService;

@RestController
@RequestMapping("/assistente/alertas")
public class AssistenteIAController {

    private final AssistenteIAService assistenteIAService;

    public AssistenteIAController(AssistenteIAService assistenteIAService) {
        this.assistenteIAService = assistenteIAService;
    }

    @GetMapping("/{id}/orientacao")
    public ResponseEntity<OrientacaoIAResponseDTO> gerarOrientacao(
            @PathVariable Integer id) {

        OrientacaoIAResponseDTO resposta =
                assistenteIAService.gerarOrientacao(id);

        return ResponseEntity.ok(resposta);
    }
}
