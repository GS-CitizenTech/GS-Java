package br.com.alertacidadao.alerta_cidadao.repository;
import java.util.Optional;
import br.com.alertacidadao.alerta_cidadao.model.PontoMonitoramento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PontoMonitoramentoRepository
        extends JpaRepository<PontoMonitoramento, Integer> {

	Optional<PontoMonitoramento> findByCodigo(String codigo);
}
