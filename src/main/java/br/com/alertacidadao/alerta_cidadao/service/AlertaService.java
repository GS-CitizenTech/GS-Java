
package br.com.alertacidadao.alerta_cidadao.service;

import br.com.alertacidadao.alerta_cidadao.dto.AlertaRequestDTO;
import br.com.alertacidadao.alerta_cidadao.dto.AlertaResponseDTO;
import br.com.alertacidadao.alerta_cidadao.event.AlertaCriadoEvent;
import br.com.alertacidadao.alerta_cidadao.exception.RecursoNaoEncontradoException;
import br.com.alertacidadao.alerta_cidadao.exception.RegraNegocioException;
import br.com.alertacidadao.alerta_cidadao.model.Alerta;
import br.com.alertacidadao.alerta_cidadao.model.LeituraEnchente;
import br.com.alertacidadao.alerta_cidadao.repository.AlertaRepository;
import br.com.alertacidadao.alerta_cidadao.repository.LeituraEnchenteRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlertaService {

    private final AlertaRepository repository;
    private final LeituraEnchenteRepository leituraRepository;
    private final AlertaProducer alertaProducer;

    public AlertaService(
            AlertaRepository repository,
            LeituraEnchenteRepository leituraRepository,
            AlertaProducer alertaProducer) {

        this.repository = repository;
        this.leituraRepository = leituraRepository;
        this.alertaProducer = alertaProducer;
    }

    public List<AlertaResponseDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    public Page<AlertaResponseDTO> listarPaginado(
            Pageable pageable) {

        return repository.findAll(pageable)
                .map(this::converterParaDTO);
    }

    public AlertaResponseDTO buscarPorId(Integer id) {

        Alerta alerta = repository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Alerta não encontrado"));

        return converterParaDTO(alerta);
    }

    public AlertaResponseDTO salvar(AlertaRequestDTO dados) {

        LeituraEnchente leitura = leituraRepository.findById(
                dados.getIdLeitura()
        ).orElseThrow(() ->
                new RecursoNaoEncontradoException(
                        "Leitura não encontrada"));

        if (repository.existsByLeitura_IdLeitura(dados.getIdLeitura())) {
            throw new RegraNegocioException(
                    "Já existe um alerta para esta leitura.");
        }

        Alerta alerta = new Alerta();

        alerta.setLeitura(leitura);
        alerta.setClassificacao(dados.getClassificacao());
        alerta.setProbabilidade(dados.getProbabilidade());
        alerta.setMensagemExplicativa(dados.getMensagemExplicativa());
        alerta.setStatus("ATIVO");
        alerta.setDataGeracao(LocalDateTime.now());

        Alerta salvo = repository.save(alerta);

        publicarEvento(salvo);

        return converterParaDTO(salvo);
    }

    @Transactional
    public String gerarAutomaticamente() {

        List<LeituraEnchente> leituras = leituraRepository.findAll();

        int gerados = 0;
        int baixoRisco = 0;
        int duplicados = 0;
        int semDados = 0;

        for (LeituraEnchente leitura : leituras) {

            if (repository.existsByLeitura_IdLeitura(
                    leitura.getIdLeitura())) {
                duplicados++;
                continue;
            }

            BigDecimal chuva1h = leitura.getChuvaMm();
            BigDecimal chuva3h = leitura.getChuva3hMm();
            BigDecimal chuva6h = leitura.getChuva6hMm();
            BigDecimal chuva24h = leitura.getChuva24hMm();

            if (chuva1h == null
                    && chuva3h == null
                    && chuva6h == null
                    && chuva24h == null) {

                semDados++;
                continue;
            }

            int maiorRisco = 0;
            String mensagem = "";

            int risco1h = avaliarRisco(
                    chuva1h, "10", "30", "50");

            if (risco1h > maiorRisco) {
                maiorRisco = risco1h;
                mensagem = "Risco identificado pelo acumulado "
                        + "de chuva em 1 hora.";
            }

            int risco3h = avaliarRisco(
                    chuva3h, "10", "30", "50");

            if (risco3h > maiorRisco) {
                maiorRisco = risco3h;
                mensagem = "Risco identificado pelo acumulado "
                        + "de chuva em 3 horas.";
            }

            int risco6h = avaliarRisco(
                    chuva6h, "15", "40", "60");

            if (risco6h > maiorRisco) {
                maiorRisco = risco6h;
                mensagem = "Risco identificado pelo acumulado "
                        + "de chuva em 6 horas.";
            }

            int risco24h = avaliarRisco(
                    chuva24h, "20", "50", "80");

            if (risco24h > maiorRisco) {
                maiorRisco = risco24h;
                mensagem = "Risco identificado pelo acumulado "
                        + "de chuva em 24 horas.";
            }

            if (maiorRisco == 0) {
                baixoRisco++;
                continue;
            }

            String classificacao;
            BigDecimal probabilidade;

            if (maiorRisco == 3) {
                classificacao = "CRITICO";
                probabilidade = new BigDecimal("95.00");
                mensagem += " Volume extremamente elevado.";
            } else if (maiorRisco == 2) {
                classificacao = "ALTO";
                probabilidade = new BigDecimal("70.00");
                mensagem += " Volume elevado. Atenção para "
                        + "possíveis alagamentos.";
            } else {
                classificacao = "MODERADO";
                probabilidade = new BigDecimal("40.00");
                mensagem += " Recomenda-se monitoramento.";
            }

            Alerta alerta = new Alerta();

            alerta.setLeitura(leitura);
            alerta.setClassificacao(classificacao);
            alerta.setProbabilidade(probabilidade);
            alerta.setMensagemExplicativa(mensagem);
            alerta.setStatus("ATIVO");
            alerta.setDataGeracao(LocalDateTime.now());

            Alerta salvo = repository.save(alerta);

            publicarEvento(salvo);

            gerados++;
        }

        return "Geração automática concluída. "
                + "Alertas gerados: " + gerados
                + ". Leituras de baixo risco: " + baixoRisco
                + ". Duplicados: " + duplicados
                + ". Leituras sem dados: " + semDados + ".";
    }

    private void publicarEvento(Alerta alerta) {

        AlertaCriadoEvent evento = new AlertaCriadoEvent(
                alerta.getIdAlerta(),
                alerta.getClassificacao(),
                alerta.getMensagemExplicativa(),
                alerta.getDataGeracao()
        );

        alertaProducer.publicarAlerta(evento);
    }

    private int avaliarRisco(
            BigDecimal valor,
            String limiteModerado,
            String limiteAlto,
            String limiteCritico) {

        if (valor == null || valor.compareTo(BigDecimal.ZERO) < 0) {
            return 0;
        }

        if (valor.compareTo(new BigDecimal(limiteCritico)) >= 0) {
            return 3;
        }

        if (valor.compareTo(new BigDecimal(limiteAlto)) >= 0) {
            return 2;
        }

        if (valor.compareTo(new BigDecimal(limiteModerado)) >= 0) {
            return 1;
        }

        return 0;
    }

    public void excluir(Integer id) {

        if (!repository.existsById(id)) {
            throw new RecursoNaoEncontradoException(
                    "Alerta não encontrado");
        }

        repository.deleteById(id);
    }

    private AlertaResponseDTO converterParaDTO(Alerta alerta) {

        AlertaResponseDTO dto = new AlertaResponseDTO();

        dto.setIdAlerta(alerta.getIdAlerta());
        dto.setIdLeitura(alerta.getLeitura().getIdLeitura());
        dto.setClassificacao(alerta.getClassificacao());
        dto.setProbabilidade(alerta.getProbabilidade());
        dto.setMensagemExplicativa(alerta.getMensagemExplicativa());
        dto.setStatus(alerta.getStatus());
        dto.setDataGeracao(alerta.getDataGeracao());
        dto.setDataResolucao(alerta.getDataResolucao());

        return dto;
    }
}
