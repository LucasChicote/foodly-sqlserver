package com.foodly.api.controller;

import com.foodly.api.dto.ProdutoRequestDTO;
import com.foodly.api.dto.ProdutoResponseDTO;
import com.foodly.api.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/categoria/{id}")
    public ResponseEntity<List<ProdutoResponseDTO>> listarPorCategoria(@PathVariable Long id) {
        return ResponseEntity.ok(service.listarPorCategoria(id));
    }

    @GetMapping("/restaurante/{id}")
    public ResponseEntity<List<ProdutoResponseDTO>> listarPorRestaurante(@PathVariable Long id) {
        return ResponseEntity.ok(service.listarPorRestaurante(id));
    }

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> salvar(@RequestBody @Valid ProdutoRequestDTO dto) {
        return ResponseEntity.status(201).body(service.salvar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid ProdutoRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
