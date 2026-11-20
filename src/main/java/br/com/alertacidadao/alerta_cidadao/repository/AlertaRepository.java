
package br.com.alertacidadao.alerta_cidadao.repository;

import br.com.alertacidadao.alerta_cidadao.model.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertaRepository extends JpaRepository<Alerta, Integer> {

    boolean existsByLeitura_IdLeitura(Integer idLeitura);
}
