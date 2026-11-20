
package br.com.alertacidadao.alerta_cidadao.event;

import java.time.LocalDateTime;

public record AlertaCriadoEvent(
        Integer idAlerta,
        String classificacao,
        String mensagem,
        LocalDateTime dataCriacao
) {
}
