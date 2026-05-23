package com.foodly.api.repository;

import com.foodly.api.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

    List<Restaurante> findByDonoId(Long donoId);
}
