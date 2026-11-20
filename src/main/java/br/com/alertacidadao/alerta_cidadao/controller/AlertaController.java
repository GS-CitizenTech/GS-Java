
package br.com.alertacidadao.alerta_cidadao.controller;

import br.com.alertacidadao.alerta_cidadao.dto.AlertaRequestDTO;
import br.com.alertacidadao.alerta_cidadao.dto.AlertaResponseDTO;
import br.com.alertacidadao.alerta_cidadao.service.AlertaService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alertas")
public class AlertaController {

    private final AlertaService service;

    public AlertaController(AlertaService service) {
        this.service = service;
    }

    @GetMapping
    public List<AlertaResponseDTO> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public AlertaResponseDTO buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public AlertaResponseDTO salvar(
            @Valid @RequestBody AlertaRequestDTO dados) {
        return service.salvar(dados);
    }

    @PostMapping("/gerar-automaticos")
    public String gerarAutomaticamente() {
        return service.gerarAutomaticamente();
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        service.excluir(id);
    }
}
