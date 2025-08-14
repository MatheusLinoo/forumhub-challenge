package br.com.alura.forumhub.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RespostaRequestDTO(Long id,
                                 @NotBlank String mensagem,
                                 @NotNull Long topicoId,
                                 @NotNull Long autorId) {
}