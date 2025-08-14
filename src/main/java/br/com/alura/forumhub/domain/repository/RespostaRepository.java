package br.com.alura.forumhub.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.forumhub.domain.model.Resposta;
import br.com.alura.forumhub.domain.model.Topico;

public interface RespostaRepository extends JpaRepository<Resposta, Long> {

    List<Resposta> findByTopicoId(Topico topico);

}
