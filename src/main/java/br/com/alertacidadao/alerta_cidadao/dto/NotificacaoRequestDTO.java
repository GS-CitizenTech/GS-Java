
package br.com.alertacidadao.alerta_cidadao.dto;

import jakarta.validation.constraints.NotNull;

public class NotificacaoRequestDTO {

    @NotNull(message = "O ID do usuário é obrigatório")
    private Integer idUsuario;

    @NotNull(message = "O ID do alerta é obrigatório")
    private Integer idAlerta;

    public NotificacaoRequestDTO() {
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
}
