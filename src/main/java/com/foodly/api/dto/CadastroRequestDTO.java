package com.foodly.api.dto;

import com.foodly.api.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CadastroRequestDTO(

        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @NotBlank(message = "E-mail é obrigatório")
        @Email(message = "E-mail inválido")
        String email,

        @NotBlank(message = "Senha é obrigatória")
        @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
        String senha,

        @NotBlank(message = "CEP é obrigatório")
        String cep,

        @NotNull(message = "Role é obrigatória")
        Role role
) {}
