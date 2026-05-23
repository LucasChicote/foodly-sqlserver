package com.foodly.api.dto;

import com.foodly.api.model.Usuario;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        String role,
        String cep,
        String rua,
        String bairro
) {
    public static UsuarioResponseDTO fromUsuario(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getRole().name(),
                usuario.getCep(),
                usuario.getRua(),
                usuario.getBairro()
        );
    }
}
