package br.com.alura.forumhub.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.alura.forumhub.domain.model.Curso;
import br.com.alura.forumhub.domain.repository.CursoRepository;
import br.com.alura.forumhub.dto.requests.CursoRequestDTO;
import br.com.alura.forumhub.dto.responses.CursoResponseDTO;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    public List<CursoResponseDTO> listar() {
        return cursoRepository.findAll().stream()
                .map(c -> new CursoResponseDTO(c.getId(), c.getNome())).toList();
    }

    public Curso cadastrar(CursoRequestDTO dados) {
        return cursoRepository.save(new Curso(dados.nome()));
    }

    public void excluir(Long id) {
        cursoRepository.findById(id)
                .ifPresentOrElse(
                        cursoRepository::delete,
                        () -> { throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado"); }
                );
    }
}
