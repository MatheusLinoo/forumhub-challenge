package br.com.alura.forumhub.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.alura.forumhub.domain.model.Usuario;
import br.com.alura.forumhub.domain.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UsuarioResponseDTO> listar() {
        return usuarioRepository.findAll().stream()
                .map(u -> new UsuarioResponseDTO(u.getId(),u.getNome(),u.getEmail())).toList();
    }

    public Usuario cadastrar(UsuarioRequestDTO dados) {
        String senha = dados.senha();
        String senhaCriptografada = passwordEncoder.encode(senha);

        Usuario usuario = new Usuario(dados.nome(), dados.email(), senhaCriptografada);
        return usuarioRepository.save(usuario);
    }

    public void excluir(Long id) {
        usuarioRepository.findById(id)
                .ifPresentOrElse(
                        usuarioRepository::delete,
                        () -> { throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"); }
                );
    }
}