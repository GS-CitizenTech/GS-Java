
package br.com.alertacidadao.alerta_cidadao.integration;

import tools.jackson.databind.JsonNode;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CemadenService {

    private final CemadenClient cemadenClient;

    @Value("${cemaden.api.codibge}")
    private String codigoIbge;

    @Value("${cemaden.api.uf}")
    private String uf;

    @Value("${cemaden.api.rede:11}")
    private String rede;

    public CemadenService(CemadenClient cemadenClient) {
        this.cemadenClient = cemadenClient;
    }

    public JsonNode consultarCidades() {
        return cemadenClient.buscarCidades(uf);
    }

    public JsonNode consultarEstacoes() {
        return cemadenClient.buscarEstacoes(codigoIbge);
    }

    public JsonNode consultarDadosCadastrais() {
        return cemadenClient.buscarDadosCadastrais(codigoIbge);
    }

    public JsonNode consultarAcumuladosRecentes() {
        return cemadenClient.buscarAcumuladosRecentes(codigoIbge);
    }

    public JsonNode consultarDadosRecentes() {
        return cemadenClient.buscarDadosRecentes(uf, rede);
    }

    public JsonNode consultarAcumuladosHistoricos(String data) {
        return cemadenClient.buscarAcumuladosHistoricos(
                codigoIbge,
                data
        );
    }
}
