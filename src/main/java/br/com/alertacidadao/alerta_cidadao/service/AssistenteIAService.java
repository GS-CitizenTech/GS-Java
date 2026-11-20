
package br.com.alertacidadao.alerta_cidadao.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import br.com.alertacidadao.alerta_cidadao.dto.OrientacaoIAResponseDTO;
import br.com.alertacidadao.alerta_cidadao.model.Alerta;
import br.com.alertacidadao.alerta_cidadao.model.LeituraEnchente;
import br.com.alertacidadao.alerta_cidadao.repository.AlertaRepository;

@Service
public class AssistenteIAService {

    private static final Logger logger =
            LoggerFactory.getLogger(AssistenteIAService.class);

    private final ChatClient chatClient;
    private final AlertaRepository alertaRepository;

    public AssistenteIAService(
            ChatClient.Builder chatClientBuilder,
            AlertaRepository alertaRepository) {

        this.chatClient = chatClientBuilder.build();
        this.alertaRepository = alertaRepository;
    }

    public OrientacaoIAResponseDTO gerarOrientacao(Integer idAlerta) {

        Alerta alerta = alertaRepository.findById(idAlerta)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Alerta não encontrado: " + idAlerta));

        LeituraEnchente leitura = alerta.getLeitura();

        String dadosAlerta = """
                DADOS DO SISTEMA ALERTA CIDADÃO:

                ID do alerta: %s
                Classificação: %s
                Probabilidade registrada: %s
                Status: %s
                Mensagem original: %s

                DADOS DA LEITURA:

                Chuva em 1 hora (mm): %s
                Chuva em 3 horas (mm): %s
                Chuva em 6 horas (mm): %s
                Chuva em 24 horas (mm): %s
                Nível da água (cm): %s
                Elevação da água (cm/h): %s
                Data e hora: %s
                Origem: %s
                """.formatted(
                alerta.getIdAlerta(),
                alerta.getClassificacao(),
                alerta.getProbabilidade(),
                alerta.getStatus(),
                alerta.getMensagemExplicativa(),
                leitura.getChuvaMm(),
                leitura.getChuva3hMm(),
                leitura.getChuva6hMm(),
                leitura.getChuva24hMm(),
                leitura.getNivelAguaCm(),
                leitura.getElevacaoCmH(),
                leitura.getDataHoraLeitura(),
                leitura.getOrigem()
        );

        String orientacao;

        try {

            orientacao = chatClient.prompt()
                    .system("""
                            Você é um assistente de orientação preventiva
                            especializado em prevenção de enchentes.

                            Sua função é orientar cidadãos com base nos
                            dados fornecidos pelo sistema Alerta Cidadão.

                            REGRAS OBRIGATÓRIAS:

                            1. Utilize somente os dados apresentados.
                            2. Nunca invente medições.
                            3. Nunca invente probabilidades.
                            4. Não altere a classificação do alerta.
                            5. Explique a situação em linguagem simples.
                            6. Apresente medidas preventivas práticas.
                            7. Informe quando os dados forem insuficientes.
                            8. Oriente a população a acompanhar a Defesa Civil.
                            9. Não substitua orientações oficiais.

                            Organize a resposta em:
                            - Explicação da situação.
                            - Riscos e cuidados.
                            - Medidas preventivas.
                            - Orientação final.
                            """)
                    .user(dadosAlerta)
                    .call()
                    .content();

            if (orientacao == null || orientacao.isBlank()) {
                throw new IllegalStateException(
                        "O Gemini retornou uma resposta vazia.");
            }

        } catch (Exception e) {

            logger.error(
                    "ERRO AO CHAMAR O GOOGLE GEMINI PARA O ALERTA {}",
                    idAlerta,
                    e
            );

            throw new RuntimeException(
                    "Falha ao gerar orientação com Gemini: "
                            + e.getMessage(),
                    e
            );
        }

        return new OrientacaoIAResponseDTO(
                alerta.getIdAlerta(),
                alerta.getClassificacao(),
                orientacao,
                "Orientação gerada por IA. Não substitui alertas e instruções oficiais da Defesa Civil."
        );
    }
}
