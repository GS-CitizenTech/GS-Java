
package br.com.alertacidadao.alerta_cidadao.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class LeituraEnchenteResponseDTO {

    private Integer idLeitura;
    private Integer idPonto;
    private BigDecimal nivelAguaCm;
    private BigDecimal chuvaMm;
    private BigDecimal elevacaoCmH;
    private LocalDateTime dataHoraLeitura;
    private String origem;
    private LocalDateTime dataRecebimento;

    public LeituraEnchenteResponseDTO() {
    }

    public LeituraEnchenteResponseDTO(
            Integer idLeitura,
            Integer idPonto,
            BigDecimal nivelAguaCm,
            BigDecimal chuvaMm,
            BigDecimal elevacaoCmH,
            LocalDateTime dataHoraLeitura,
            String origem,
            LocalDateTime dataRecebimento
    ) {
        this.idLeitura = idLeitura;
        this.idPonto = idPonto;
        this.nivelAguaCm = nivelAguaCm;
        this.chuvaMm = chuvaMm;
        this.elevacaoCmH = elevacaoCmH;
        this.dataHoraLeitura = dataHoraLeitura;
        this.origem = origem;
        this.dataRecebimento = dataRecebimento;
    }

    public Integer getIdLeitura() {
        return idLeitura;
    }

    public void setIdLeitura(Integer idLeitura) {
        this.idLeitura = idLeitura;
    }

    public Integer getIdPonto() {
        return idPonto;
    }

    public void setIdPonto(Integer idPonto) {
        this.idPonto = idPonto;
    }

    public BigDecimal getNivelAguaCm() {
        return nivelAguaCm;
    }

    public void setNivelAguaCm(BigDecimal nivelAguaCm) {
        this.nivelAguaCm = nivelAguaCm;
    }

    public BigDecimal getChuvaMm() {
        return chuvaMm;
    }

    public void setChuvaMm(BigDecimal chuvaMm) {
        this.chuvaMm = chuvaMm;
    }

    public BigDecimal getElevacaoCmH() {
        return elevacaoCmH;
    }

    public void setElevacaoCmH(BigDecimal elevacaoCmH) {
        this.elevacaoCmH = elevacaoCmH;
    }

    public LocalDateTime getDataHoraLeitura() {
        return dataHoraLeitura;
    }

    public void setDataHoraLeitura(LocalDateTime dataHoraLeitura) {
        this.dataHoraLeitura = dataHoraLeitura;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public LocalDateTime getDataRecebimento() {
        return dataRecebimento;
    }

    public void setDataRecebimento(LocalDateTime dataRecebimento) {
        this.dataRecebimento = dataRecebimento;
    }
}

