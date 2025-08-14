package br.com.alura.forumhub.dto.responses;

import java.time.LocalDateTime;

import br.com.alura.forumhub.domain.model.Resposta;

public record RespostaResponseDTO(Long id,
                                  String mensagem,
                                  LocalDateTime dataInc,
                                  UsuarioResponseDTO autor) {
    public RespostaResponseDTO(Resposta resposta) {
        this(resposta.getId(),
                resposta.getMensagem(),
                resposta.getDataInc(),
                new UsuarioResponseDTO(
                        resposta.getAutor().getId(),
                        resposta.getAutor().getNome(),
                        resposta.getAutor().getEmail()));
    }
}