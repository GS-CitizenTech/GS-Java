
package br.com.alertacidadao.alerta_cidadao.service;

import br.com.alertacidadao.alerta_cidadao.dto.UsuarioRequestDTO;
import br.com.alertacidadao.alerta_cidadao.dto.UsuarioResponseDTO;
import br.com.alertacidadao.alerta_cidadao.exception.RecursoNaoEncontradoException;
import br.com.alertacidadao.alerta_cidadao.model.Usuario;
import br.com.alertacidadao.alerta_cidadao.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(
            UsuarioRepository repository,
            PasswordEncoder passwordEncoder) {

        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UsuarioResponseDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    public Page<UsuarioResponseDTO> listarPaginado(
            Pageable pageable) {

        return repository.findAll(pageable)
                .map(this::converterParaDTO);
    }

    public UsuarioResponseDTO buscarPorId(Integer id) {

        Usuario usuario = repository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Usuário não encontrado"));

        return converterParaDTO(usuario);
    }

    public UsuarioResponseDTO salvar(UsuarioRequestDTO dados) {

        Usuario usuario = new Usuario();

        usuario.setNome(dados.getNome());
        usuario.setEmail(dados.getEmail());
        usuario.setSenha(passwordEncoder.encode(dados.getSenha()));
        usuario.setPapel(dados.getPapel());
        usuario.setAtivo("S");

        Usuario salvo = repository.save(usuario);

        return converterParaDTO(salvo);
    }

    public void excluir(Integer id) {

        if (!repository.existsById(id)) {
            throw new RecursoNaoEncontradoException(
                    "Usuário não encontrado");
        }

        repository.deleteById(id);
    }

    private UsuarioResponseDTO converterParaDTO(Usuario usuario) {

        UsuarioResponseDTO dto = new UsuarioResponseDTO();

        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setPapel(usuario.getPapel());
        dto.setAtivo(usuario.getAtivo());

        return dto;
    }
}
