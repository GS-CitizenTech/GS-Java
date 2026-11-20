
package br.com.alertacidadao.alerta_cidadao.service;

import br.com.alertacidadao.alerta_cidadao.dto.PontoMonitoramentoRequestDTO;
import br.com.alertacidadao.alerta_cidadao.dto.PontoMonitoramentoResponseDTO;
import br.com.alertacidadao.alerta_cidadao.exception.RecursoNaoEncontradoException;
import br.com.alertacidadao.alerta_cidadao.model.PontoMonitoramento;
import br.com.alertacidadao.alerta_cidadao.repository.PontoMonitoramentoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PontoMonitoramentoService {

    private final PontoMonitoramentoRepository repository;

    public PontoMonitoramentoService(
            PontoMonitoramentoRepository repository) {
        this.repository = repository;
    }

    public List<PontoMonitoramentoResponseDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    public Page<PontoMonitoramentoResponseDTO> listarPaginado(
            Pageable pageable) {

        return repository.findAll(pageable)
                .map(this::converterParaDTO);
    }

    public PontoMonitoramentoResponseDTO buscarPorId(Integer id) {

        PontoMonitoramento ponto = repository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Ponto de monitoramento não encontrado"));

        return converterParaDTO(ponto);
    }

    public PontoMonitoramentoResponseDTO salvar(
            PontoMonitoramentoRequestDTO dados) {

        PontoMonitoramento ponto = new PontoMonitoramento();

        ponto.setCodigo(dados.getCodigo());
        ponto.setNome(dados.getNome());
        ponto.setBairro(dados.getBairro());
        ponto.setCidade(dados.getCidade());
        ponto.setUf(dados.getUf());
        ponto.setLatitude(dados.getLatitude());
        ponto.setLongitude(dados.getLongitude());
        ponto.setAtivo("S");
        ponto.setDataCadastro(LocalDateTime.now());

        PontoMonitoramento salvo = repository.save(ponto);

        return converterParaDTO(salvo);
    }

    public void excluir(Integer id) {

        if (!repository.existsById(id)) {
            throw new RecursoNaoEncontradoException(
                    "Ponto de monitoramento não encontrado");
        }

        repository.deleteById(id);
    }

    private PontoMonitoramentoResponseDTO converterParaDTO(
            PontoMonitoramento ponto) {

        PontoMonitoramentoResponseDTO dto =
                new PontoMonitoramentoResponseDTO();

        dto.setIdPonto(ponto.getIdPonto());
        dto.setCodigo(ponto.getCodigo());
        dto.setNome(ponto.getNome());
        dto.setBairro(ponto.getBairro());
        dto.setCidade(ponto.getCidade());
        dto.setUf(ponto.getUf());
        dto.setLatitude(ponto.getLatitude());
        dto.setLongitude(ponto.getLongitude());
        dto.setAtivo(ponto.getAtivo());
        dto.setDataCadastro(ponto.getDataCadastro());

        return dto;
    }
}
