package br.com.alura.forumhub.controller;

import jakarta.transaction.Transactional;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("cursos")
@SecurityRequirement(name = "bearer-key")
public class CursoController {
    
    @Autowired
    private CursoService cursoService;

    @GetMapping
    public ResponseEntity<List<CursoResponseDTO>> listar() {
        List<CursoResponseDTO> cursos = cursoService.listar();
        return ResponseEntity.ok(cursos);
    }

    @PostMapping
    public ResponseEntity<CursoResponseDTO> cadastrar(@RequestBody @Valid CursoRequestDTO dados) {
        Curso curso = cursoService.cadastrar(dados);

        URI uri = URI.create("/cursos/" + curso.getId());

        return ResponseEntity
                .created(uri)
                .body(new CursoResponseDTO(curso.getId(), curso.getNome()));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        cursoService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}