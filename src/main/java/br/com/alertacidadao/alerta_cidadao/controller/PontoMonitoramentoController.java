
package br.com.alertacidadao.alerta_cidadao.controller;

import br.com.alertacidadao.alerta_cidadao.dto.PontoMonitoramentoRequestDTO;
import br.com.alertacidadao.alerta_cidadao.dto.PontoMonitoramentoResponseDTO;
import br.com.alertacidadao.alerta_cidadao.service.PontoMonitoramentoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pontos")
public class PontoMonitoramentoController {

    private final PontoMonitoramentoService service;

    public PontoMonitoramentoController(
            PontoMonitoramentoService service) {
        this.service = service;
    }

    @GetMapping
    public List<PontoMonitoramentoResponseDTO> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/paginado")
    public Page<PontoMonitoramentoResponseDTO> listarPaginado(
            @PageableDefault(size = 10, sort = "idPonto")
            Pageable pageable) {

        return service.listarPaginado(pageable);
    }

    @GetMapping("/{id}")
    public PontoMonitoramentoResponseDTO buscarPorId(
            @PathVariable Integer id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PontoMonitoramentoResponseDTO salvar(
            @Valid @RequestBody PontoMonitoramentoRequestDTO dados) {
        return service.salvar(dados);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Integer id) {
        service.excluir(id);
    }
}
