
package br.com.alertacidadao.alerta_cidadao.repository;

import br.com.alertacidadao.alerta_cidadao.model.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificacaoRepository extends JpaRepository<Notificacao, Integer> {

    boolean existsByUsuario_IdUsuarioAndAlerta_IdAlerta(
            Integer idUsuario,
            Integer idAlerta
    );
}


