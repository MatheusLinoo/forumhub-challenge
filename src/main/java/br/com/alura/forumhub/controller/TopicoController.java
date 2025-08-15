package br.com.alura.forumhub.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.alura.forumhub.domain.model.Topico;
import br.com.alura.forumhub.domain.service.RespostaService;
import br.com.alura.forumhub.domain.service.TopicoService;
import br.com.alura.forumhub.dto.requests.AtualizaRequestStatusDTO;
import br.com.alura.forumhub.dto.requests.RespostaRequestDTO;
import br.com.alura.forumhub.dto.requests.TopicoRequestDTO;
import br.com.alura.forumhub.dto.responses.RespostaResponseDTO;
import br.com.alura.forumhub.dto.responses.TopicoResponseDTO;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @Autowired
    private RespostaService respostaService;

    @GetMapping
    public ResponseEntity<Page<TopicoResponseDTO>> listar(@PageableDefault(size = 10) Pageable pageable) {
        Page<TopicoResponseDTO> page = topicoService.listar(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/recentes")
    public ResponseEntity<List<TopicoResponseDTO>> listarTop10OrderByDataIncAsc(){
        List<TopicoResponseDTO> topicos = topicoService.listarTop10OrderByDataIncAsc();
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<TopicoResponseDTO>> buscarPorCursoEAno(
            @PageableDefault(size = 10) Pageable pageable,
            @RequestParam String curso,
            @RequestParam int ano) {
        Page<TopicoResponseDTO> page = topicoService.buscarPorCursoEAno(curso, ano, pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicoResponseDTO> listarId(@PathVariable Long id) {
        TopicoResponseDTO topico = topicoService.listarId(id);

        if (topico == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(topico);
    }

    @PostMapping
    public ResponseEntity<TopicoResponseDTO> cadastrar(@RequestBody @Valid TopicoRequestDTO dados) {

    System.out.println("Chegou no controller cadastro");
        Topico topico =  topicoService.cadastrar(dados);

        URI uri = URI.create("/topicos/" + topico.getId());

        return ResponseEntity
                .created(uri)
                .body(new TopicoResponseDTO(topico));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicoResponseDTO> atualizar(@RequestBody TopicoRequestDTO dados, @PathVariable Long id) {
        Topico topico = topicoService.buscarTopico(id);
        topico.atualizar(dados);

        return ResponseEntity.ok(new TopicoResponseDTO(topico));
    }

    @PutMapping("{id}/status")
    @Transactional
    public ResponseEntity<TopicoResponseDTO> atualizarStatus(@RequestBody AtualizaRequestStatusDTO dados, @PathVariable Long id) {
        Topico topico = topicoService.atualizarStatus(id, dados);
        return ResponseEntity.ok(new TopicoResponseDTO(topico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        topicoService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}