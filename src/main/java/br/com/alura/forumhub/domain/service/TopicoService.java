package br.com.alura.forumhub.domain.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.alura.forumhub.domain.model.Curso;
import br.com.alura.forumhub.domain.model.Status;
import br.com.alura.forumhub.domain.model.Topico;
import br.com.alura.forumhub.domain.model.Usuario;
import br.com.alura.forumhub.domain.repository.CursoRepository;
import br.com.alura.forumhub.domain.repository.TopicoRepository;
import br.com.alura.forumhub.domain.repository.UsuarioRepository;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CursoRepository cursoRepository;

    public Topico cadastrar(TopicoRequestDTO dados) {
        Usuario autor = usuarioRepository.findById(dados.autor().id())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Curso curso = cursoRepository.findById(dados.curso().id())
                .orElseThrow(() -> new RuntimeException("Curso não encontrado"));

        Topico topico = new Topico(
                dados.titulo(),
                dados.mensagem(),
                LocalDateTime.now(),
                Status.NAO_RESPONDIDO,
                autor,
                curso);

        return topicoRepository.save(topico);
    }

    public Page<TopicoResponseDTO> listar(Pageable pageable) {
        return topicoRepository.findAll(pageable)
                .map(t -> new TopicoResponseDTO(
                        t.getId(),
                        t.getTitulo(),
                        t.getMensagem(),
                        t.getStatus(),
                        t.getDataInc(),
                        new UsuarioResponseDTO(
                                t.getAutor().getId(),
                                t.getAutor().getNome(),
                                t.getAutor().getEmail()),
                        new CursoResponseDTO(
                                t.getCurso().getId(),
                                t.getCurso().getNome()),
                        t.getRespostas()
                                .stream()
                                .map(RespostaResponseDTO::new)
                                .toList()));
    }

    public List<TopicoResponseDTO> listarTop10OrderByDataIncAsc() {
        return topicoRepository.findTop10ByOrderByDataIncAsc();
    }

    public TopicoResponseDTO listarId(Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);

        if (topico.isPresent()) {
            return new TopicoResponseDTO(
                    topico.get().getId(),
                    topico.get().getTitulo(),
                    topico.get().getMensagem(),
                    topico.get().getStatus(),
                    topico.get().getDataInc(),
                    new UsuarioResponseDTO(
                            topico.get().getAutor().getId(),
                            topico.get().getAutor().getNome(),
                            topico.get().getAutor().getEmail()),
                    new CursoResponseDTO(
                            topico.get().getCurso().getId(),
                            topico.get().getCurso().getNome()),
                    topico.get().getRespostas()
                            .stream()
                            .map(RespostaResponseDTO::new)
                            .toList());
        }
        return null;
    }

    public Page<TopicoResponseDTO> buscarPorCursoEAno(String curso, int ano, Pageable pageable) {
        return topicoRepository.buscarPorCursoEAno(curso, ano, pageable)
                .map(t ->
                        new TopicoResponseDTO(
                            t.getId(),
                            t.getTitulo(),
                            t.getMensagem(),
                            t.getStatus(),
                            t.getDataInc(),
                            new UsuarioResponseDTO(
                                    t.getAutor().getId(),
                                    t.getAutor().getNome(),
                                    t.getAutor().getEmail()),
                                    new CursoResponseDTO(
                                            t.getCurso().getId(),
                                            t.getCurso().getNome()),
                                t.getRespostas()
                                        .stream()
                                        .map(RespostaResponseDTO::new)
                                        .toList()));
    }

    public Topico buscarTopico(Long id) {
        return topicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico não encontrado"));
    }

    public void excluir(Long id) {
        topicoRepository.findById(id)
                .ifPresentOrElse(
                        topicoRepository::delete,
                        () -> { throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico não encontrado"); }
                );
    }

    public Topico atualizarStatus(Long id, AtualizarRequestStatusDTO dados) {
        Topico topico = buscarTopico(id);
        topico.atualizarStatus(dados);

        System.out.println(topico.getStatus());
        return topicoRepository.save(topico);

    }
}