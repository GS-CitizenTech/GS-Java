
package br.com.alertacidadao.alerta_cidadao.integration;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tools.jackson.databind.JsonNode;

import br.com.alertacidadao.alerta_cidadao.model.LeituraEnchente;
import br.com.alertacidadao.alerta_cidadao.model.PontoMonitoramento;
import br.com.alertacidadao.alerta_cidadao.repository.LeituraEnchenteRepository;
import br.com.alertacidadao.alerta_cidadao.repository.PontoMonitoramentoRepository;

@Service
public class CemadenSincronizacaoService {

    private final CemadenService cemadenService;
    private final PontoMonitoramentoRepository pontoRepository;
    private final LeituraEnchenteRepository leituraRepository;

    public CemadenSincronizacaoService(
            CemadenService cemadenService,
            PontoMonitoramentoRepository pontoRepository,
            LeituraEnchenteRepository leituraRepository) {

        this.cemadenService = cemadenService;
        this.pontoRepository = pontoRepository;
        this.leituraRepository = leituraRepository;
    }

    @Transactional
    public String sincronizarEstacoes() {

        JsonNode dados = cemadenService.consultarDadosRecentes();

        if (dados == null || !dados.isArray()) {
            throw new IllegalStateException(
                    "O CEMADEN não retornou uma lista de estações."
            );
        }

        int cadastrados = 0;
        int atualizados = 0;

        for (JsonNode item : dados) {

            String codigo = texto(item, "codestacao");

            if (codigo == null || codigo.isBlank()) {
                continue;
            }

            var pontoOptional = pontoRepository.findByCodigo(codigo);

            boolean novo = pontoOptional.isEmpty();

            PontoMonitoramento ponto = pontoOptional.orElseGet(
                    PontoMonitoramento::new
            );

            ponto.setCodigo(codigo);
            ponto.setNome(texto(item, "nome"));
            ponto.setCidade(texto(item, "cidade"));
            ponto.setUf(texto(item, "uf"));
            ponto.setLatitude(decimal(item, "latitude"));
            ponto.setLongitude(decimal(item, "longitude"));
            ponto.setAtivo("S");

            if (novo) {
                ponto.setDataCadastro(LocalDateTime.now());
                cadastrados++;
            } else {
                atualizados++;
            }

            pontoRepository.save(ponto);
        }

        return "Sincronização concluída. Novas estações: "
                + cadastrados + ". Atualizadas: " + atualizados + ".";
    }

    @Transactional
    public String sincronizarLeituras() {

        JsonNode dados = cemadenService.consultarAcumuladosRecentes();

        if (dados == null || !dados.isArray()) {
            throw new IllegalStateException(
                    "O CEMADEN não retornou uma lista de acumulados."
            );
        }

        int cadastradas = 0;
        int duplicadas = 0;
        int semEstacao = 0;
        int semValor = 0;

        for (JsonNode item : dados) {

            String codigo = texto(item, "codestacao");
            String dataHoraTexto = texto(item, "datahora");

            if (codigo == null || dataHoraTexto == null) {
                continue;
            }

            BigDecimal chuva1h = decimal(item, "acc1hr");
            BigDecimal chuva3h = decimal(item, "acc3hr");
            BigDecimal chuva6h = decimal(item, "acc6hr");
            BigDecimal chuva24h = decimal(item, "acc24hr");

            if (chuva1h == null
                    && chuva3h == null
                    && chuva6h == null
                    && chuva24h == null) {

                semValor++;
                continue;
            }

            var pontoOptional = pontoRepository.findByCodigo(codigo);

            if (pontoOptional.isEmpty()) {
                semEstacao++;
                continue;
            }

            PontoMonitoramento ponto = pontoOptional.get();

            LocalDateTime dataHora = LocalDateTime.parse(dataHoraTexto);

            if (leituraRepository
                    .existsByPontoMonitoramentoAndDataHoraLeituraAndOrigem(
                            ponto, dataHora, "CEMADEN")) {

                duplicadas++;
                continue;
            }

            LeituraEnchente leitura = new LeituraEnchente();

            leitura.setPontoMonitoramento(ponto);
            leitura.setChuvaMm(chuva1h);
            leitura.setChuva3hMm(chuva3h);
            leitura.setChuva6hMm(chuva6h);
            leitura.setChuva24hMm(chuva24h);

            leitura.setDataHoraLeitura(dataHora);
            leitura.setDataRecebimento(LocalDateTime.now());
            leitura.setOrigem("CEMADEN");

            leituraRepository.save(leitura);

            cadastradas++;
        }

        return "Sincronização de leituras concluída. "
                + "Cadastradas: " + cadastradas
                + ". Duplicadas: " + duplicadas
                + ". Estações não encontradas: " + semEstacao
                + ". Valores indisponíveis: " + semValor + ".";
    }

    private String texto(JsonNode item, String campo) {

        JsonNode valor = item.get(campo);

        if (valor == null || valor.isNull()) {
            return null;
        }

        return valor.asString();
    }

    private BigDecimal decimal(JsonNode item, String campo) {

        JsonNode valor = item.get(campo);

        if (valor == null || valor.isNull()) {
            return null;
        }

        return new BigDecimal(valor.asString());
    }
}
