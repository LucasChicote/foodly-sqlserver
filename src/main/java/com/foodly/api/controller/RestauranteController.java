package com.foodly.api.controller;

import com.foodly.api.dto.RestauranteDTO;
import com.foodly.api.service.RestauranteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    private final RestauranteService service;

    public RestauranteController(RestauranteService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<RestauranteDTO.Response>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/meus")
    public ResponseEntity<List<RestauranteDTO.Response>> listarMeus() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(service.listarDoOwner(email));
    }

    @PostMapping
    public ResponseEntity<RestauranteDTO.Response> criar(@RequestBody @Valid RestauranteDTO.Request dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.status(201).body(service.criar(dto, email));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestauranteDTO.Response> atualizar(@PathVariable Long id, @RequestBody @Valid RestauranteDTO.Request dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(service.atualizar(id, dto, email));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        service.deletar(id, email);
        return ResponseEntity.noContent().build();
    }
}
