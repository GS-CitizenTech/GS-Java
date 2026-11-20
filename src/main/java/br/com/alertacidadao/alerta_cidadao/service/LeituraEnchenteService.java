
package br.com.alertacidadao.alerta_cidadao.service;

import br.com.alertacidadao.alerta_cidadao.dto.LeituraEnchenteRequestDTO;
import br.com.alertacidadao.alerta_cidadao.dto.LeituraEnchenteResponseDTO;
import br.com.alertacidadao.alerta_cidadao.exception.RecursoNaoEncontradoException;
import br.com.alertacidadao.alerta_cidadao.model.LeituraEnchente;
import br.com.alertacidadao.alerta_cidadao.model.PontoMonitoramento;
import br.com.alertacidadao.alerta_cidadao.repository.LeituraEnchenteRepository;
import br.com.alertacidadao.alerta_cidadao.repository.PontoMonitoramentoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LeituraEnchenteService {

    private final LeituraEnchenteRepository repository;
    private final PontoMonitoramentoRepository pontoRepository;

    public LeituraEnchenteService(
            LeituraEnchenteRepository repository,
            PontoMonitoramentoRepository pontoRepository) {

        this.repository = repository;
        this.pontoRepository = pontoRepository;
    }

    public List<LeituraEnchenteResponseDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    public Page<LeituraEnchenteResponseDTO> listarPaginado(
            Pageable pageable) {

        return repository.findAll(pageable)
                .map(this::converterParaDTO);
    }

    public LeituraEnchenteResponseDTO buscarPorId(Integer id) {

        LeituraEnchente leitura = repository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Leitura não encontrada"));

        return converterParaDTO(leitura);
    }

    public LeituraEnchenteResponseDTO salvar(
            LeituraEnchenteRequestDTO dados) {

        PontoMonitoramento ponto = pontoRepository.findById(
                dados.getIdPonto()
        ).orElseThrow(() ->
                new RecursoNaoEncontradoException(
                        "Ponto de monitoramento não encontrado"));

        LeituraEnchente leitura = new LeituraEnchente();

        leitura.setPontoMonitoramento(ponto);
        leitura.setNivelAguaCm(dados.getNivelAguaCm());
        leitura.setChuvaMm(dados.getChuvaMm());
        leitura.setElevacaoCmH(dados.getElevacaoCmH());
        leitura.setDataHoraLeitura(dados.getDataHoraLeitura());
        leitura.setOrigem(dados.getOrigem());
        leitura.setDataRecebimento(LocalDateTime.now());

        LeituraEnchente salva = repository.save(leitura);

        return converterParaDTO(salva);
    }

    public void excluir(Integer id) {

        if (!repository.existsById(id)) {
            throw new RecursoNaoEncontradoException(
                    "Leitura não encontrada");
        }

        repository.deleteById(id);
    }

    private LeituraEnchenteResponseDTO converterParaDTO(
            LeituraEnchente leitura) {

        LeituraEnchenteResponseDTO dto =
                new LeituraEnchenteResponseDTO();

        dto.setIdLeitura(leitura.getIdLeitura());
        dto.setIdPonto(
                leitura.getPontoMonitoramento().getIdPonto()
        );
        dto.setNivelAguaCm(leitura.getNivelAguaCm());
        dto.setChuvaMm(leitura.getChuvaMm());
        dto.setElevacaoCmH(leitura.getElevacaoCmH());
        dto.setDataHoraLeitura(leitura.getDataHoraLeitura());
        dto.setOrigem(leitura.getOrigem());
        dto.setDataRecebimento(leitura.getDataRecebimento());

        return dto;
    }
}
