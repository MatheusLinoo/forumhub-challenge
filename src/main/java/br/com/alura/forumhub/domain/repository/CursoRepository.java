package br.com.alura.forumhub.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.forumhub.domain.model.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {

}
