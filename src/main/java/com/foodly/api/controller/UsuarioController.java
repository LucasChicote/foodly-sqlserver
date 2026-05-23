package com.foodly.api.controller;

import com.foodly.api.dto.UsuarioResponseDTO;
import com.foodly.api.model.Endereco;
import com.foodly.api.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/cep/{cep}")
    public ResponseEntity<Endereco> buscarCep(@PathVariable String cep) {
        return ResponseEntity.ok(service.buscarCep(cep));
    }
}
