
package br.com.alertacidadao.alerta_cidadao.integration;

import tools.jackson.databind.JsonNode;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/integracao/cemaden")
public class CemadenController {

    private final CemadenService cemadenService;
    private final CemadenSincronizacaoService sincronizacaoService;

    public CemadenController(
            CemadenService cemadenService,
            CemadenSincronizacaoService sincronizacaoService) {

        this.cemadenService = cemadenService;
        this.sincronizacaoService = sincronizacaoService;
    }

    @GetMapping("/cidades")
    public ResponseEntity<JsonNode> consultarCidades() {
        return ResponseEntity.ok(cemadenService.consultarCidades());
    }

    @GetMapping("/estacoes")
    public ResponseEntity<JsonNode> consultarEstacoes() {
        return ResponseEntity.ok(cemadenService.consultarEstacoes());
    }

    @GetMapping("/dados-cadastrais")
    public ResponseEntity<JsonNode> consultarDadosCadastrais() {
        return ResponseEntity.ok(cemadenService.consultarDadosCadastrais());
    }

    @GetMapping("/acumulados-recentes")
    public ResponseEntity<JsonNode> consultarAcumuladosRecentes() {
        return ResponseEntity.ok(cemadenService.consultarAcumuladosRecentes());
    }

    @GetMapping("/dados-recentes")
    public ResponseEntity<JsonNode> consultarDadosRecentes() {
        return ResponseEntity.ok(cemadenService.consultarDadosRecentes());
    }

    @GetMapping("/acumulados-historicos")
    public ResponseEntity<JsonNode> consultarAcumuladosHistoricos(
            @RequestParam String data) {

        return ResponseEntity.ok(
                cemadenService.consultarAcumuladosHistoricos(data)
        );
    }

    @PostMapping("/sincronizar-estacoes")
    public ResponseEntity<String> sincronizarEstacoes() {
        return ResponseEntity.ok(
                sincronizacaoService.sincronizarEstacoes()
        );
    }

    @PostMapping("/sincronizar-leituras")
    public ResponseEntity<String> sincronizarLeituras() {
        return ResponseEntity.ok(
                sincronizacaoService.sincronizarLeituras()
        );
    }
}
