package br.com.alura.forumhub.domain.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "cursos")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    
    @OneToMany(mappedBy = "curso")
    private List<Topico> topicos;

    public Curso(String nome) {
        this.nome = nome;
    }
}