package br.com.alura.forumhub.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.forumhub.domain.model.Usuario;
import br.com.alura.forumhub.domain.service.TokenService;
import br.com.alura.forumhub.dto.requests.AutenticacaoUsuario;
import br.com.alura.forumhub.dto.responses.LoginResponseDTO;
import br.com.alura.forumhub.dto.responses.UsuarioResponseDTO;

@RestController
@RequestMapping("login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<LoginResponseDTO> efetuarLogin(@RequestBody @Valid AutenticacaoUsuario dados) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();
        String tokenJWT = tokenService.gerarToken(usuarioLogado);

        var usuarioDTO = new UsuarioResponseDTO(
                usuarioLogado.getId(),
                usuarioLogado.getNome(),
                usuarioLogado.getEmail()
        );

        var dto = new LoginResponseDTO(tokenJWT, usuarioDTO);

        return ResponseEntity.ok(dto);
    }
}
