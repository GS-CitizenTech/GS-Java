
package br.com.alertacidadao.alerta_cidadao.integration;

import java.util.Map;

import tools.jackson.databind.JsonNode;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class CemadenClient {

    private final RestClient restClient;
    private final String baseUrl;
    private final String token;

    public CemadenClient(
            @Value("${cemaden.api.base-url}") String baseUrl,
            @Value("${cemaden.api.token}") String token) {

        this.restClient = RestClient.create();
        this.baseUrl = baseUrl;
        this.token = token;
    }

    private JsonNode executarGet(
            String endpoint,
            Map<String, String> parametros) {

        if (token == null || token.isBlank()) {
            throw new IllegalStateException(
                    "Token do CEMADEN não configurado."
            );
        }

        UriComponentsBuilder uriBuilder =
                UriComponentsBuilder.fromUriString(baseUrl)
                        .path(endpoint);

        parametros.forEach((chave, valor) -> {
            if (valor != null && !valor.isBlank()) {
                uriBuilder.queryParam(chave, valor);
            }
        });

        return restClient.get()
                .uri(uriBuilder.build().encode().toUri())
                .header("token", token)
                .retrieve()
                .body(JsonNode.class);
    }

    public JsonNode buscarCidades(String uf) {
        return executarGet(
                "/pcds-cadastro/cidades",
                Map.of(
                        "uf", uf,
                        "formato", "JSON"
                )
        );
    }

    public JsonNode buscarEstacoes(String codigoIbge) {
        return executarGet(
                "/pcds-cadastro/estacoes",
                Map.of(
                        "codibge", codigoIbge,
                        "formato", "JSON"
                )
        );
    }

    public JsonNode buscarDadosCadastrais(String codigoIbge) {
        return executarGet(
                "/pcds-cadastro/dados-cadastrais",
                Map.of(
                        "codibge", codigoIbge,
                        "formato", "JSON"
                )
        );
    }

    public JsonNode buscarAcumuladosRecentes(String codigoIbge) {
        return executarGet(
                "/pcds-acum/acumulados-recentes",
                Map.of(
                        "codibge", codigoIbge,
                        "formato", "JSON"
                )
        );
    }

    public JsonNode buscarDadosRecentes(String uf, String rede) {
        return executarGet(
                "/pcds/pcds-dados-recentes",
                Map.of(
                        "uf", uf,
                        "rede", rede,
                        "formato", "JSON"
                )
        );
    }

    public JsonNode buscarAcumuladosHistoricos(
            String codigoIbge,
            String data) {

        return executarGet(
                "/pcds-acum/acumulados-historicos",
                Map.of(
                        "codibge", codigoIbge,
                        "data", data,
                        "formato", "JSON"
                )
        );
    }
}
