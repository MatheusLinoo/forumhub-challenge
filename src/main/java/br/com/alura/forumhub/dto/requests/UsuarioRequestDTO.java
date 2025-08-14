package br.com.alura.forumhub.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioRequestDTO(Long id,
                                @NotBlank String nome,
                                @Email String email,
                                @NotBlank @Size(min = 6, max = 10) String senha) {
}
