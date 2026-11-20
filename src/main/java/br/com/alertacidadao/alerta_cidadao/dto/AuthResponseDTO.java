package br.com.alertacidadao.alerta_cidadao.dto;

public record AuthResponseDTO(
    String token,
    String tipo,
    String nome,
    String papel
) {}