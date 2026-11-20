
package br.com.alertacidadao.alerta_cidadao.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.alertacidadao.alerta_cidadao.dto.AuthResponseDTO;
import br.com.alertacidadao.alerta_cidadao.dto.CadastroRequestDTO;
import br.com.alertacidadao.alerta_cidadao.dto.LoginRequestDTO;
import br.com.alertacidadao.alerta_cidadao.model.Usuario;
import br.com.alertacidadao.alerta_cidadao.repository.UsuarioRepository;
import br.com.alertacidadao.alerta_cidadao.security.JwtService;

@Service
public class AuthService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UsuarioRepository repository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponseDTO cadastrar(CadastroRequestDTO dto) {

        if (repository.existsByEmail(dto.email())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Este email já está cadastrado."
            );
        }

        Usuario usuario = new Usuario();

        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setSenha(passwordEncoder.encode(dto.senha()));
        usuario.setPapel("ROLE_USER");
        usuario.setAtivo("S");

        // TESTE PARA IDENTIFICAR O VALOR ENVIADO AO BANCO
        System.out.println("========== TESTE CADASTRO ==========");
        System.out.println("PAPEL ENVIADO: [" + usuario.getPapel() + "]");
        System.out.println("ATIVO ENVIADO: [" + usuario.getAtivo() + "]");
        System.out.println("====================================");

        Usuario salvo = repository.save(usuario);

        return gerarResposta(salvo);
    }

    public AuthResponseDTO login(LoginRequestDTO dto) {

        Usuario usuario = repository.findByEmail(dto.email())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Email ou senha inválidos."
                ));

        if (!passwordEncoder.matches(dto.senha(), usuario.getSenha())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Email ou senha inválidos."
            );
        }

        if (!"S".equalsIgnoreCase(usuario.getAtivo())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Usuário inativo."
            );
        }

        return gerarResposta(usuario);
    }

    private AuthResponseDTO gerarResposta(Usuario usuario) {

        return new AuthResponseDTO(
                jwtService.gerarToken(usuario),
                "Bearer",
                usuario.getNome(),
                usuario.getPapel()
        );
    }
}
