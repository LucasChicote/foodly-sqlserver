package com.foodly.api.controller;

import com.foodly.api.dto.PedidoDTO;
import com.foodly.api.model.StatusPedido;
import com.foodly.api.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PedidoDTO.Response> realizarPedido(@RequestBody @Valid PedidoDTO.Request dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.status(201).body(service.realizarPedido(dto, email));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PedidoDTO.Response> atualizarStatus(
            @PathVariable Long id,
            @RequestParam StatusPedido novoStatus) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(service.atualizarStatus(id, novoStatus, email));
    }

    @GetMapping("/meus")
    public ResponseEntity<List<PedidoDTO.Response>> meusPedidos() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(service.listarPedidosDoCliente(email));
    }

    @GetMapping("/restaurante/{restauranteId}")
    public ResponseEntity<List<PedidoDTO.Response>> pedidosDoRestaurante(@PathVariable Long restauranteId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(service.listarPedidosDoRestaurante(restauranteId, email));
    }
}
