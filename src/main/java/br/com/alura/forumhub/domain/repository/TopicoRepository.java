package br.com.alura.forumhub.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.alura.forumhub.domain.model.Topico;
import br.com.alura.forumhub.dto.responses.TopicoResponseDTO;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    List<TopicoResponseDTO> findTop10ByOrderByDateAsc();

    @Query("""
            SELECT t
            FROM Topico t
            WHERE t.curso.nome = :curso
            AND
            FUNCTION('YEAR', t.dataInc) = :ano
            """)
        Page<Topico> buscarPorCursoEAno(@Param("curso") String curso, @Param("ano") int ano, Pageable pageable);

}
