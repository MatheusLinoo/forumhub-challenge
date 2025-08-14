package br.com.alura.forumhub.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alura.forumhub.domain.model.Resposta;
import br.com.alura.forumhub.domain.model.Topico;
import br.com.alura.forumhub.domain.model.Usuario;
import br.com.alura.forumhub.domain.repository.RespostaRepository;
import br.com.alura.forumhub.domain.repository.TopicoRepository;
import br.com.alura.forumhub.domain.repository.UsuarioRepository;
import br.com.alura.forumhub.dto.requests.RespostaRequestDTO;
import br.com.alura.forumhub.dto.responses.RespostaResponseDTO;
import br.com.alura.forumhub.dto.responses.UsuarioResponseDTO;

@Service
public class RespostaService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RespostaRepository respostaRepository;

    public List<RespostaResponseDTO> listar() {
        return respostaRepository.findAll().stream()
                .map(r -> new RespostaResponseDTO(
                        r.getId(),
                        r.getMensagem(),
                        r.getDataInc(),
                        new UsuarioResponseDTO(
                                r.getAutor().getId(),
                                r.getAutor().getNome(),
                                r.getAutor().getEmail())
                )).toList();
    }

    public RespostaResponseDTO cadastrar(RespostaRequestDTO dto) {
        Topico topico = topicoRepository.findById(dto.topicoId())
                .orElseThrow(() -> new RuntimeException("Tópico não encontrado"));

        Usuario autor = usuarioRepository.findById(dto.autorId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Resposta resposta = new Resposta(
                dto.mensagem(),
                topico,
                autor
        );

        respostaRepository.save(resposta);

        return new RespostaResponseDTO(
                resposta.getId(),
                resposta.getMensagem(),
                resposta.getDataInc(),
                new UsuarioResponseDTO( resposta.getAutor().getId(),
                        resposta.getAutor().getNome(),
                        resposta.getAutor().getEmail())
        );
    }
}