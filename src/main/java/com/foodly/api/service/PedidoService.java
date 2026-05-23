package com.foodly.api.service;

import com.foodly.api.dto.PedidoDTO;
import com.foodly.api.model.*;
import com.foodly.api.repository.PedidoRepository;
import com.foodly.api.repository.ProdutoRepository;
import com.foodly.api.repository.RestauranteRepository;
import com.foodly.api.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final RestauranteRepository restauranteRepository;
    private final UsuarioRepository usuarioRepository;

    public PedidoService(PedidoRepository pedidoRepository,
                         ProdutoRepository produtoRepository,
                         RestauranteRepository restauranteRepository,
                         UsuarioRepository usuarioRepository) {
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
        this.restauranteRepository = restauranteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public PedidoDTO.Response realizarPedido(PedidoDTO.Request dto, String emailCliente) {
        Usuario cliente = usuarioRepository.findUsuarioByEmail(emailCliente)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        Restaurante restaurante = restauranteRepository.findById(dto.restauranteId())
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado: " + dto.restauranteId()));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setRestaurante(restaurante);
        pedido.setStatus(StatusPedido.PENDENTE);

        double total = 0.0;

        for (PedidoDTO.ItemRequest itemDto : dto.itens()) {
            Produto produto = produtoRepository.findById(itemDto.produtoId())
                    .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + itemDto.produtoId()));
            if (!produto.getRestaurante().getId().equals(restaurante.getId())) {
                throw new IllegalArgumentException(
                        "Produto '" + produto.getNome() + "' não pertence ao restaurante selecionado"
                );
            }

            ItemPedido item = new ItemPedido();
            item.setPedido(pedido);
            item.setProduto(produto);
            item.setQuantidade(itemDto.quantidade());
            item.setPrecoUnitario(produto.getPreco());

            pedido.getItens().add(item);
            total += produto.getPreco() * itemDto.quantidade();
        }

        pedido.setTotal(total);
        return PedidoDTO.Response.fromPedido(pedidoRepository.save(pedido));
    }

    @Transactional
    public PedidoDTO.Response atualizarStatus(Long pedidoId, StatusPedido novoStatus, String emailOwner) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado: " + pedidoId));

        if (!pedido.getRestaurante().getDono().getEmail().equals(emailOwner)) {
            throw new SecurityException("Você não tem permissão para alterar este pedido");
        }

        if (pedido.getStatus() == StatusPedido.ENTREGUE || pedido.getStatus() == StatusPedido.CANCELADO) {
            throw new IllegalStateException("Pedido já finalizado — status não pode ser alterado");
        }

        pedido.setStatus(novoStatus);
        return PedidoDTO.Response.fromPedido(pedidoRepository.save(pedido));
    }

    public List<PedidoDTO.Response> listarPedidosDoCliente(String emailCliente) {
        Usuario cliente = usuarioRepository.findUsuarioByEmail(emailCliente)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        return pedidoRepository.findByClienteIdOrderByCriadoEmDesc(cliente.getId())
                .stream()
                .map(PedidoDTO.Response::fromPedido)
                .toList();
    }

    public List<PedidoDTO.Response> listarPedidosDoRestaurante(Long restauranteId, String emailOwner) {
        Restaurante restaurante = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado"));

        if (!restaurante.getDono().getEmail().equals(emailOwner)) {
            throw new SecurityException("Você não tem permissão para ver pedidos deste restaurante");
        }

        return pedidoRepository.findByRestauranteIdOrderByCriadoEmDesc(restauranteId)
                .stream()
                .map(PedidoDTO.Response::fromPedido)
                .toList();
    }
}
