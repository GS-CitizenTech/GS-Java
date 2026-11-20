package br.com.alertacidadao.alerta_cidadao.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import br.com.alertacidadao.alerta_cidadao.dto.AuthResponseDTO;
import br.com.alertacidadao.alerta_cidadao.dto.CadastroRequestDTO;
import br.com.alertacidadao.alerta_cidadao.dto.LoginRequestDTO;
import br.com.alertacidadao.alerta_cidadao.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<AuthResponseDTO> cadastrar(
            @Valid @RequestBody CadastroRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authService.cadastrar(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }
}