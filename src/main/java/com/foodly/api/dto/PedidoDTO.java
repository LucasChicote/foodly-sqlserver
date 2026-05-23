package com.foodly.api.dto;

import com.foodly.api.model.Pedido;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;

public class PedidoDTO {

    public record ItemRequest(
            @NotNull(message = "ID do produto é obrigatório")
            Long produtoId,

            @NotNull(message = "Quantidade é obrigatória")
            @Positive(message = "Quantidade deve ser maior que zero")
            Integer quantidade
    ) {}

    public record Request(
            @NotNull(message = "ID do restaurante é obrigatório")
            Long restauranteId,

            @NotEmpty(message = "O pedido deve ter pelo menos um item")
            List<ItemRequest> itens
    ) {}

    public record ItemResponse(
            String nomeProduto,
            Integer quantidade,
            Double precoUnitario,
            Double subtotal
    ) {}

    public record Response(
            Long id,
            String status,
            Double total,
            LocalDateTime criadoEm,
            String nomeCliente,
            String nomeRestaurante,
            List<ItemResponse> itens
    ) {
        public static Response fromPedido(Pedido pedido) {
            List<ItemResponse> itensResponse = pedido.getItens().stream()
                    .map(item -> new ItemResponse(
                            item.getProduto().getNome(),
                            item.getQuantidade(),
                            item.getPrecoUnitario(),
                            item.getQuantidade() * item.getPrecoUnitario()
                    ))
                    .toList();

            return new Response(
                    pedido.getId(),
                    pedido.getStatus().name(),
                    pedido.getTotal(),
                    pedido.getCriadoEm(),
                    pedido.getCliente().getNome(),
                    pedido.getRestaurante().getNome(),
                    itensResponse
            );
        }
    }
}
