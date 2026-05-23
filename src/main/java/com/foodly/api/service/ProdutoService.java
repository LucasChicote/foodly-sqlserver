package com.foodly.api.service;

import com.foodly.api.dto.ProdutoRequestDTO;
import com.foodly.api.dto.ProdutoResponseDTO;
import com.foodly.api.model.Categoria;
import com.foodly.api.model.Produto;
import com.foodly.api.model.Restaurante;
import com.foodly.api.repository.CategoriaRepository;
import com.foodly.api.repository.ProdutoRepository;
import com.foodly.api.repository.RestauranteRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final RestauranteRepository restauranteRepository;

    public ProdutoService(ProdutoRepository produtoRepository,
                          CategoriaRepository categoriaRepository,
                          RestauranteRepository restauranteRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.restauranteRepository = restauranteRepository;
    }

    public ProdutoResponseDTO salvar(ProdutoRequestDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada: " + dto.categoriaId()));

        Restaurante restaurante = restauranteRepository.findById(dto.restauranteId())
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado: " + dto.restauranteId()));

        Produto produto = new Produto();
        produto.setNome(dto.nome());
        produto.setDescricao(dto.descricao());
        produto.setPreco(dto.preco());
        produto.setImagemUrl(dto.imagemUrl());
        produto.setCategoria(categoria);
        produto.setRestaurante(restaurante);

        return ProdutoResponseDTO.fromProduto(produtoRepository.save(produto));
    }

    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO dto) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + id));

        Categoria categoria = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada: " + dto.categoriaId()));

        Restaurante restaurante = restauranteRepository.findById(dto.restauranteId())
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado: " + dto.restauranteId()));

        produto.setNome(dto.nome());
        produto.setDescricao(dto.descricao());
        produto.setPreco(dto.preco());
        produto.setImagemUrl(dto.imagemUrl());
        produto.setCategoria(categoria);
        produto.setRestaurante(restaurante);

        return ProdutoResponseDTO.fromProduto(produtoRepository.save(produto));
    }

    public List<ProdutoResponseDTO> listarTodos() {
        return produtoRepository.findAll()
                .stream()
                .map(ProdutoResponseDTO::fromProduto)
                .toList();
    }

    public List<ProdutoResponseDTO> listarPorCategoria(Long categoriaId) {
        return produtoRepository.findByCategoriaId(categoriaId)
                .stream()
                .map(ProdutoResponseDTO::fromProduto)
                .toList();
    }

    public List<ProdutoResponseDTO> listarPorRestaurante(Long restauranteId) {
        return produtoRepository.findByRestauranteId(restauranteId)
                .stream()
                .map(ProdutoResponseDTO::fromProduto)
                .toList();
    }

    public void deletar(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new IllegalArgumentException("Produto não encontrado: " + id);
        }
        produtoRepository.deleteById(id);
    }
}
