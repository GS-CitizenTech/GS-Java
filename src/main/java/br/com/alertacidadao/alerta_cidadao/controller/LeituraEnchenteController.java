
package br.com.alertacidadao.alerta_cidadao.controller;

import br.com.alertacidadao.alerta_cidadao.dto.LeituraEnchenteRequestDTO;
import br.com.alertacidadao.alerta_cidadao.dto.LeituraEnchenteResponseDTO;
import br.com.alertacidadao.alerta_cidadao.service.LeituraEnchenteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leituras")
public class LeituraEnchenteController {

    private final LeituraEnchenteService service;

    public LeituraEnchenteController(LeituraEnchenteService service) {
        this.service = service;
    }

    @GetMapping
    public List<LeituraEnchenteResponseDTO> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/paginado")
    public Page<LeituraEnchenteResponseDTO> listarPaginado(
            @PageableDefault(size = 10, sort = "idLeitura")
            Pageable pageable) {

        return service.listarPaginado(pageable);
    }

    @GetMapping("/{id}")
    public LeituraEnchenteResponseDTO buscarPorId(
            @PathVariable Integer id) {

        return service.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LeituraEnchenteResponseDTO salvar(
            @Valid @RequestBody LeituraEnchenteRequestDTO dados) {

        return service.salvar(dados);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Integer id) {
        service.excluir(id);
    }
}
