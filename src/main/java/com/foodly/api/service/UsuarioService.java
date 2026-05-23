package com.foodly.api.service;

import com.foodly.api.client.ViaCepClient;
import com.foodly.api.dto.CadastroRequestDTO;
import com.foodly.api.dto.UsuarioResponseDTO;
import com.foodly.api.model.Endereco;
import com.foodly.api.model.Usuario;
import com.foodly.api.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final ViaCepClient viaCepClient;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository repository,
                          ViaCepClient viaCepClient,
                          PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.viaCepClient = viaCepClient;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioResponseDTO cadastrar(CadastroRequestDTO dto) {
        if (repository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("E-mail já cadastrado: " + dto.email());
        }

        Endereco endereco = viaCepClient.buscarCep(dto.cep());

        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setSenha(passwordEncoder.encode(dto.senha()));
        usuario.setCep(dto.cep());
        usuario.setRua(endereco.getLogradouro());
        usuario.setBairro(endereco.getBairro());
        usuario.setRole(dto.role());

        return UsuarioResponseDTO.fromUsuario(repository.save(usuario));
    }

    public List<UsuarioResponseDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(UsuarioResponseDTO::fromUsuario)
                .toList();
    }

    public Endereco buscarCep(String cep) {
        return viaCepClient.buscarCep(cep);
    }
}
