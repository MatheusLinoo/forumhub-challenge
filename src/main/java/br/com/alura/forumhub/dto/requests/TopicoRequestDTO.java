package br.com.alura.forumhub.dto.requests;

import java.time.LocalDateTime;

import br.com.alura.forumhub.domain.model.Status;
import br.com.alura.forumhub.dto.responses.CursoResponseDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TopicoRequestDTO(Long id,
                               @NotBlank(message = "O título é obrigatório!") String titulo,
                               @NotBlank(message = "A mensagem é obrigatória!") String mensagem,
                               @NotNull Status status,
                               @NotNull LocalDateTime dataInc,
                               UsuarioIdRequestDTO autor,
                               @NotNull(message = "O curso é obrigatório!") CursoResponseDTO curso) {
}