package com.foodly.api.controller;

import com.foodly.api.dto.CadastroRequestDTO;
import com.foodly.api.dto.LoginRequestDTO;
import com.foodly.api.dto.LoginResponseDTO;
import com.foodly.api.dto.UsuarioResponseDTO;
import com.foodly.api.model.Usuario;
import com.foodly.api.service.TokenService;
import com.foodly.api.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    private final AuthenticationManager authManager;
    private final TokenService tokenService;
    private final UsuarioService usuarioService;

    public AutenticacaoController(AuthenticationManager authManager,
                                  TokenService tokenService,
                                  UsuarioService usuarioService) {
        this.authManager = authManager;
        this.tokenService = tokenService;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto) {
        var authToken = new UsernamePasswordAuthenticationToken(dto.email(), dto.senha());
        var authentication = authManager.authenticate(authToken);
        var usuario = (Usuario) authentication.getPrincipal();

        String token = tokenService.gerarToken(usuario);

        return ResponseEntity.ok(new LoginResponseDTO(
                token,
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getRole().name()
        ));
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioResponseDTO> register(@RequestBody @Valid CadastroRequestDTO dto) {
        UsuarioResponseDTO response = usuarioService.cadastrar(dto);
        return ResponseEntity.status(201).body(response);
    }
}
