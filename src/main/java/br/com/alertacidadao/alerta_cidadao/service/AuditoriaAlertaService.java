
package br.com.alertacidadao.alerta_cidadao.service;

import br.com.alertacidadao.alerta_cidadao.dto.AuditoriaAlertaResponseDTO;
import br.com.alertacidadao.alerta_cidadao.exception.RecursoNaoEncontradoException;
import br.com.alertacidadao.alerta_cidadao.model.AuditoriaAlerta;
import br.com.alertacidadao.alerta_cidadao.repository.AuditoriaAlertaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditoriaAlertaService {

    private final AuditoriaAlertaRepository repository;

    public AuditoriaAlertaService(
            AuditoriaAlertaRepository repository) {

        this.repository = repository;
    }

    public List<AuditoriaAlertaResponseDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    public Page<AuditoriaAlertaResponseDTO> listarPaginado(
            Pageable pageable) {

        return repository.findAll(pageable)
                .map(this::converterParaDTO);
    }

    public AuditoriaAlertaResponseDTO buscarPorId(Integer id) {

        AuditoriaAlerta auditoria = repository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Registro de auditoria não encontrado"));

        return converterParaDTO(auditoria);
    }

    private AuditoriaAlertaResponseDTO converterParaDTO(
            AuditoriaAlerta auditoria) {

        AuditoriaAlertaResponseDTO dto =
                new AuditoriaAlertaResponseDTO();

        dto.setIdAuditoria(auditoria.getIdAuditoria());
        dto.setIdAlerta(auditoria.getAlerta().getIdAlerta());
        dto.setOperacao(auditoria.getOperacao());
        dto.setStatusAnterior(auditoria.getStatusAnterior());
        dto.setStatusNovo(auditoria.getStatusNovo());
        dto.setDataOperacao(auditoria.getDataOperacao());

        return dto;
    }
}
