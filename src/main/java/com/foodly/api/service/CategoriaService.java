package com.foodly.api.service;

import com.foodly.api.model.Categoria;
import com.foodly.api.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    public Categoria salvar(Categoria categoria) {
        if (repository.existsByNome(categoria.getNome())) {
            throw new IllegalArgumentException("Categoria já existe: " + categoria.getNome());
        }
        return repository.save(categoria);
    }

    public List<Categoria> listar() {
        return repository.findAll();
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Categoria não encontrada: " + id);
        }
        repository.deleteById(id);
    }
}
