package com.foodly.api.repository;

import com.foodly.api.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByClienteIdOrderByCriadoEmDesc(Long clienteId);

    List<Pedido> findByRestauranteIdOrderByCriadoEmDesc(Long restauranteId);
}
