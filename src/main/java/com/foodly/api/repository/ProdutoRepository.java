package com.foodly.api.repository;

import com.foodly.api.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByCategoriaId(Long categoriaId);

    List<Produto> findByRestauranteId(Long restauranteId);
}
