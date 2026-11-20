
package br.com.alertacidadao.alerta_cidadao.service;

import br.com.alertacidadao.alerta_cidadao.dto.NotificacaoRequestDTO;
import br.com.alertacidadao.alerta_cidadao.dto.NotificacaoResponseDTO;
import br.com.alertacidadao.alerta_cidadao.exception.RecursoNaoEncontradoException;
import br.com.alertacidadao.alerta_cidadao.exception.RegraNegocioException;
import br.com.alertacidadao.alerta_cidadao.model.Alerta;
import br.com.alertacidadao.alerta_cidadao.model.Notificacao;
import br.com.alertacidadao.alerta_cidadao.model.Usuario;
import br.com.alertacidadao.alerta_cidadao.repository.AlertaRepository;
import br.com.alertacidadao.alerta_cidadao.repository.NotificacaoRepository;
import br.com.alertacidadao.alerta_cidadao.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificacaoService {

    private final NotificacaoRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final AlertaRepository alertaRepository;

    public NotificacaoService(
            NotificacaoRepository repository,
            UsuarioRepository usuarioRepository,
            AlertaRepository alertaRepository) {

        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.alertaRepository = alertaRepository;
    }

    public List<NotificacaoResponseDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    public Page<NotificacaoResponseDTO> listarPaginado(
            Pageable pageable) {

        return repository.findAll(pageable)
                .map(this::converterParaDTO);
    }

    public NotificacaoResponseDTO buscarPorId(Integer id) {

        Notificacao notificacao = repository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Notificação não encontrada"));

        return converterParaDTO(notificacao);
    }

    public NotificacaoResponseDTO salvar(NotificacaoRequestDTO dados) {

        Usuario usuario = usuarioRepository.findById(
                dados.getIdUsuario()
        ).orElseThrow(() ->
                new RecursoNaoEncontradoException(
                        "Usuário não encontrado"));

        Alerta alerta = alertaRepository.findById(
                dados.getIdAlerta()
        ).orElseThrow(() ->
                new RecursoNaoEncontradoException(
                        "Alerta não encontrado"));

        if (repository.existsByUsuario_IdUsuarioAndAlerta_IdAlerta(
                dados.getIdUsuario(),
                dados.getIdAlerta())) {

            throw new RegraNegocioException(
                    "Este usuário já possui uma notificação para este alerta.");
        }

        Notificacao notificacao = new Notificacao();

        notificacao.setUsuario(usuario);
        notificacao.setAlerta(alerta);
        notificacao.setDataEnvio(LocalDateTime.now());
        notificacao.setLida("N");
        notificacao.setDataLeitura(null);

        Notificacao salva = repository.save(notificacao);

        return converterParaDTO(salva);
    }

    public void excluir(Integer id) {

        if (!repository.existsById(id)) {
            throw new RecursoNaoEncontradoException(
                    "Notificação não encontrada");
        }

        repository.deleteById(id);
    }

    private NotificacaoResponseDTO converterParaDTO(
            Notificacao notificacao) {

        NotificacaoResponseDTO dto = new NotificacaoResponseDTO();

        dto.setIdNotificacao(notificacao.getIdNotificacao());
        dto.setIdUsuario(notificacao.getUsuario().getIdUsuario());
        dto.setIdAlerta(notificacao.getAlerta().getIdAlerta());
        dto.setDataEnvio(notificacao.getDataEnvio());
        dto.setLida(notificacao.getLida());
        dto.setDataLeitura(notificacao.getDataLeitura());

        return dto;
    }
}
