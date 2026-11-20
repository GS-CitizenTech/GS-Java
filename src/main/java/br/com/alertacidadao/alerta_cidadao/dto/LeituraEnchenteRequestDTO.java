
package br.com.alertacidadao.alerta_cidadao.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class LeituraEnchenteRequestDTO {

    @NotNull(message = "O ID do ponto é obrigatório")
    private Integer idPonto;

    private BigDecimal nivelAguaCm;
    private BigDecimal chuvaMm;
    private BigDecimal elevacaoCmH;

    @NotNull(message = "A data e hora da leitura são obrigatórias")
    private LocalDateTime dataHoraLeitura;

    @NotBlank(message = "A origem da leitura é obrigatória")
    private String origem;

    public LeituraEnchenteRequestDTO() {
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
}

