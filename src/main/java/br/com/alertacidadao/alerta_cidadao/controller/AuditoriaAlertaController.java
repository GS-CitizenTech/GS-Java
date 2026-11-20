
package br.com.alertacidadao.alerta_cidadao.controller;

import br.com.alertacidadao.alerta_cidadao.dto.AuditoriaAlertaResponseDTO;
import br.com.alertacidadao.alerta_cidadao.service.AuditoriaAlertaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auditorias")
public class AuditoriaAlertaController {

    private final AuditoriaAlertaService service;

    public AuditoriaAlertaController(AuditoriaAlertaService service) {
        this.service = service;
    }

    @GetMapping
    public List<AuditoriaAlertaResponseDTO> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/paginado")
    public Page<AuditoriaAlertaResponseDTO> listarPaginado(
            @PageableDefault(size = 10, sort = "idAuditoria")
            Pageable pageable) {

        return service.listarPaginado(pageable);
    }

    @GetMapping("/{id}")
    public AuditoriaAlertaResponseDTO buscarPorId(
            @PathVariable Integer id) {

        return service.buscarPorId(id);
    }
}
