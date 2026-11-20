
package br.com.alertacidadao.alerta_cidadao.controller;

import br.com.alertacidadao.alerta_cidadao.dto.UsuarioRequestDTO;
import br.com.alertacidadao.alerta_cidadao.dto.UsuarioResponseDTO;
import br.com.alertacidadao.alerta_cidadao.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping
    public List<UsuarioResponseDTO> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/paginado")
    public Page<UsuarioResponseDTO> listarPaginado(
            @PageableDefault(size = 10, sort = "idUsuario")
            Pageable pageable) {

        return service.listarPaginado(pageable);
    }

    @GetMapping("/{id}")
    public UsuarioResponseDTO buscarPorId(
            @PathVariable Integer id) {

        return service.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponseDTO salvar(
            @Valid @RequestBody UsuarioRequestDTO dados) {

        return service.salvar(dados);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Integer id) {
        service.excluir(id);
    }
}
