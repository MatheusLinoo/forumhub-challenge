package br.com.alura.forumhub.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

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
    public ResponseEntity<Page<TopicoResponseDTO>> buscarPorCursoEStatus(
            @PageableDefault(size = 10) Pageable pageable,
            @RequestParam String curso,
            @RequestParam Status ano) {
        Page<TopicoResponseDTO> page = topicoService.buscarPorCursoEStatus(curso, ano, pageable);
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

    @PutMapping("status/{id}")
    @Transactional
    public ResponseEntity<TopicoResponseDTO> atualizarStatus(@RequestBody AtualizarRequestStatusDTO dados, @PathVariable Long id) {
        Topico topico = topicoService.atualizarStatus(id, dados);
        return ResponseEntity.ok(new TopicoResponseDTO(topico));
    }
    
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        topicoService.excluir(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/respostas")
    public ResponseEntity<RespostaResponseDTO> responderTopico(@PathVariable Long id, @RequestBody @Valid RespostaRequestDTO dados) {
        RespostaRequestDTO dadosComTopico = new RespostaRequestDTO(
                null,
                dados.mensagem(),
                id,
                dados.autorId()
        );

        RespostaResponseDTO respostaDTO = respostaService.cadastrar(dadosComTopico);

        URI uri = URI.create("/topicos/" + id + "/respostas/" + respostaDTO.id());
        return ResponseEntity.created(uri).body(respostaDTO);
    }
}
