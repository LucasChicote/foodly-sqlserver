package com.foodly.api.dto;

import com.foodly.api.model.Produto;

public record ProdutoResponseDTO(
        Long id,
        String nome,
        String descricao,
        Double preco,
        String imagemUrl,
        String categoria,
        String restaurante
) {
    public static ProdutoResponseDTO fromProduto(Produto produto) {
        return new ProdutoResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getImagemUrl(),
                produto.getCategoria() != null ? produto.getCategoria().getNome() : null,
                produto.getRestaurante() != null ? produto.getRestaurante().getNome() : null
        );
    }
}
