package com.delivery.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.delivery.domain.model.Pedido;
import com.delivery.domain.model.Restaurante;
import com.delivery.domain.model.Usuario;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

	public Optional<Pedido> findByCodigo(@Param("codigo") String codigoPedido);
	
	public Page<Pedido> findByRestaurante(Restaurante restaruante, Pageable page);
	
	public Page<Pedido> findByCliente(Usuario cliente, Pageable page);

}
