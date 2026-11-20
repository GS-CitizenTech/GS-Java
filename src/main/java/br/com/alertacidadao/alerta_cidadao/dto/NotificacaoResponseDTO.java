
package br.com.alertacidadao.alerta_cidadao.dto;

import java.time.LocalDateTime;

public class NotificacaoResponseDTO {

    private Integer idNotificacao;
    private Integer idUsuario;
    private Integer idAlerta;
    private LocalDateTime dataEnvio;
    private String lida;
    private LocalDateTime dataLeitura;

    public NotificacaoResponseDTO() {
    }

    public NotificacaoResponseDTO(
            Integer idNotificacao,
            Integer idUsuario,
            Integer idAlerta,
            LocalDateTime dataEnvio,
            String lida,
            LocalDateTime dataLeitura
    ) {
        this.idNotificacao = idNotificacao;
        this.idUsuario = idUsuario;
        this.idAlerta = idAlerta;
        this.dataEnvio = dataEnvio;
        this.lida = lida;
        this.dataLeitura = dataLeitura;
    }

    public Integer getIdNotificacao() {
        return idNotificacao;
    }

    public void setIdNotificacao(Integer idNotificacao) {
        this.idNotificacao = idNotificacao;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdAlerta() {
        return idAlerta;
    }

    public void setIdAlerta(Integer idAlerta) {
        this.idAlerta = idAlerta;
    }

    public LocalDateTime getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(LocalDateTime dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public String getLida() {
        return lida;
    }

    public void setLida(String lida) {
        this.lida = lida;
    }

    public LocalDateTime getDataLeitura() {
        return dataLeitura;
    }

    public void setDataLeitura(LocalDateTime dataLeitura) {
        this.dataLeitura = dataLeitura;
    }
}
