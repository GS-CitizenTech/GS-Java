package br.com.alertacidadao.alerta_cidadao.dto;

public record OrientacaoIAResponseDTO(
        Integer idAlerta,
        String classificacao,
        String orientacao,
        String aviso
) {
}