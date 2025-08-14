package br.com.alura.forumhub.dto.requests;

import br.com.alura.forumhub.domain.model.Curso;
import jakarta.validation.constraints.NotBlank;

public record CursoRequestDTO(Long id,
                              @NotBlank(message = "O nome é obrigatório!") String nome) {
    public CursoRequestDTO(Curso curso) {
        this(curso.getId(), curso.getNome());
    }
}
