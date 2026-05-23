package com.foodly.api.dto;

import com.foodly.api.model.Restaurante;
import jakarta.validation.constraints.NotBlank;

public class RestauranteDTO {

    public record Request(
            @NotBlank(message = "Nome do restaurante é obrigatório")
            String nome,

            String descricao,

            @NotBlank(message = "Categoria do restaurante é obrigatória")
            String categoria,

            String imagemUrl
    ) {}

    public record Response(
            Long id,
            String nome,
            String descricao,
            String categoria,
            String imagemUrl,
            String nomeDono
    ) {
        public static Response fromRestaurante(Restaurante r) {
            return new Response(
                    r.getId(),
                    r.getNome(),
                    r.getDescricao(),
                    r.getCategoria(),
                    r.getImagemUrl(),
                    r.getDono() != null ? r.getDono().getNome() : null
            );
        }
    }
}
