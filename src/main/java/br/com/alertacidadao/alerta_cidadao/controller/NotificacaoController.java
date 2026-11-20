
package br.com.alertacidadao.alerta_cidadao.controller;

import br.com.alertacidadao.alerta_cidadao.dto.NotificacaoRequestDTO;
import br.com.alertacidadao.alerta_cidadao.dto.NotificacaoResponseDTO;
import br.com.alertacidadao.alerta_cidadao.service.NotificacaoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificacoes")
public class NotificacaoController {

    private final NotificacaoService service;

    public NotificacaoController(NotificacaoService service) {
        this.service = service;
    }

    @GetMapping
    public List<NotificacaoResponseDTO> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/paginado")
    public Page<NotificacaoResponseDTO> listarPaginado(
            @PageableDefault(size = 10, sort = "idNotificacao")
            Pageable pageable) {

        return service.listarPaginado(pageable);
    }

    @GetMapping("/{id}")
    public NotificacaoResponseDTO buscarPorId(
            @PathVariable Integer id) {

        return service.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NotificacaoResponseDTO salvar(
            @Valid @RequestBody NotificacaoRequestDTO dados) {

        return service.salvar(dados);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Integer id) {
        service.excluir(id);
    }
}
