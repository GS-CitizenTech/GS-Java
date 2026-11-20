
package br.com.alertacidadao.alerta_cidadao.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alertacidadao.alerta_cidadao.model.LeituraEnchente;
import br.com.alertacidadao.alerta_cidadao.model.PontoMonitoramento;

@Repository
public interface LeituraEnchenteRepository
        extends JpaRepository<LeituraEnchente, Integer> {

    boolean existsByPontoMonitoramentoAndDataHoraLeituraAndOrigem(
            PontoMonitoramento pontoMonitoramento,
            LocalDateTime dataHoraLeitura,
            String origem
    );
}

