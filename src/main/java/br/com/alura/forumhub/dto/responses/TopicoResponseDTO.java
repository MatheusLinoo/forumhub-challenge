package br.com.alura.forumhub.dto.responses;

import java.time.LocalDateTime;
import java.util.List;

import br.com.alura.forumhub.domain.model.Status;
import br.com.alura.forumhub.domain.model.Topico;

public record TopicoResponseDTO(Long id,
                                String titulo,
                                String mensagem,
                                Status status,
                                LocalDateTime dataInc,
                                UsuarioResponseDTO autor,
                                CursoResponseDTO curso,
                                List<RespostaResponseDTO> respostas) {
    public TopicoResponseDTO(Topico topico) {
        this(topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getStatus(),
                topico.getDataInc(),
                new UsuarioResponseDTO(
                        topico.getAutor().getId(),
                        topico.getAutor().getNome(),
                        topico.getAutor().getEmail()),
                        new CursoResponseDTO(
                                topico.getCurso().getId(),
                                topico.getCurso().getNome()),
                topico.getRespostas()
                        .stream()
                        .map(RespostaResponseDTO::new)
                        .toList());
    }
}