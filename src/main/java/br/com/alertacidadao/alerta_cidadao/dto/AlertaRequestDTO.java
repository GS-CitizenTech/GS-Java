
package br.com.alertacidadao.alerta_cidadao.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;

import java.math.BigDecimal;

public class AlertaRequestDTO {

    @NotNull(message = "O ID da leitura é obrigatório")
    private Integer idLeitura;

    @NotBlank(message = "A classificação é obrigatória")
    @Size(max = 20)
    private String classificacao;

    @DecimalMin(value = "0.00")
    @DecimalMax(value = "100.00")
    private BigDecimal probabilidade;

    @Size(max = 500)
    private String mensagemExplicativa;

    public AlertaRequestDTO() {
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
}

