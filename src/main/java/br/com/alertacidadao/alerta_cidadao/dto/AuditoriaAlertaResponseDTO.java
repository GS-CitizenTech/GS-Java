
package br.com.alertacidadao.alerta_cidadao.dto;

import java.time.LocalDateTime;

public class AuditoriaAlertaResponseDTO {

    private Integer idAuditoria;
    private Integer idAlerta;
    private String operacao;
    private String statusAnterior;
    private String statusNovo;
    private LocalDateTime dataOperacao;

    public AuditoriaAlertaResponseDTO() {
    }

    public AuditoriaAlertaResponseDTO(
            Integer idAuditoria,
            Integer idAlerta,
            String operacao,
            String statusAnterior,
            String statusNovo,
            LocalDateTime dataOperacao
    ) {
        this.idAuditoria = idAuditoria;
        this.idAlerta = idAlerta;
        this.operacao = operacao;
        this.statusAnterior = statusAnterior;
        this.statusNovo = statusNovo;
        this.dataOperacao = dataOperacao;
    }

    public Integer getIdAuditoria() {
        return idAuditoria;
    }

    public void setIdAuditoria(Integer idAuditoria) {
        this.idAuditoria = idAuditoria;
    }

    public Integer getIdAlerta() {
        return idAlerta;
    }

    public void setIdAlerta(Integer idAlerta) {
        this.idAlerta = idAlerta;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public String getStatusAnterior() {
        return statusAnterior;
    }

    public void setStatusAnterior(String statusAnterior) {
        this.statusAnterior = statusAnterior;
    }

    public String getStatusNovo() {
        return statusNovo;
    }

    public void setStatusNovo(String statusNovo) {
        this.statusNovo = statusNovo;
    }

    public LocalDateTime getDataOperacao() {
        return dataOperacao;
    }

    public void setDataOperacao(LocalDateTime dataOperacao) {
        this.dataOperacao = dataOperacao;
    }
}

