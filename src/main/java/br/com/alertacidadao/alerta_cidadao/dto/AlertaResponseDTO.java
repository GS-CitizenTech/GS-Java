
package br.com.alertacidadao.alerta_cidadao.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AlertaResponseDTO {

    private Integer idAlerta;
    private Integer idLeitura;
    private String classificacao;
    private BigDecimal probabilidade;
    private String mensagemExplicativa;
    private String status;
    private LocalDateTime dataGeracao;
    private LocalDateTime dataResolucao;

    public AlertaResponseDTO() {
    }

    public AlertaResponseDTO(
            Integer idAlerta,
            Integer idLeitura,
            String classificacao,
            BigDecimal probabilidade,
            String mensagemExplicativa,
            String status,
            LocalDateTime dataGeracao,
            LocalDateTime dataResolucao
    ) {
        this.idAlerta = idAlerta;
        this.idLeitura = idLeitura;
        this.classificacao = classificacao;
        this.probabilidade = probabilidade;
        this.mensagemExplicativa = mensagemExplicativa;
        this.status = status;
        this.dataGeracao = dataGeracao;
        this.dataResolucao = dataResolucao;
    }

    public Integer getIdAlerta() {
        return idAlerta;
    }

    public void setIdAlerta(Integer idAlerta) {
        this.idAlerta = idAlerta;
    }

    public Integer getIdLeitura() {
        return idLeitura;
    }

    public void setIdLeitura(Integer idLeitura) {
        this.idLeitura = idLeitura;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }

    public BigDecimal getProbabilidade() {
        return probabilidade;
    }

    public void setProbabilidade(BigDecimal probabilidade) {
        this.probabilidade = probabilidade;
    }

    public String getMensagemExplicativa() {
        return mensagemExplicativa;
    }

    public void setMensagemExplicativa(String mensagemExplicativa) {
        this.mensagemExplicativa = mensagemExplicativa;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDataGeracao() {
        return dataGeracao;
    }

    public void setDataGeracao(LocalDateTime dataGeracao) {
        this.dataGeracao = dataGeracao;
    }

    public LocalDateTime getDataResolucao() {
        return dataResolucao;
    }

    public void setDataResolucao(LocalDateTime dataResolucao) {
        this.dataResolucao = dataResolucao;
    }
}

