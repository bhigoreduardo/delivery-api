package com.delivery.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.delivery.domain.repository.PedidoFluxoService;

@RestController
@RequestMapping("/pedidos/{codigoPedido}")
public class PedidoFluxoController {

	@Autowired
	private PedidoFluxoService pedidoFluxoService;

	@PutMapping("/confirmacao")
	public ResponseEntity<Void> updateConfirmar(@PathVariable String codigoPedido) {
		pedidoFluxoService.updateConfirmar(codigoPedido);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/entrega")
	public ResponseEntity<Void> updateEntregar(@PathVariable String codigoPedido) {
		pedidoFluxoService.updateEntregar(codigoPedido);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/cancelamento")
	public ResponseEntity<Void> updateCancelar(@PathVariable String codigoPedido) {
		pedidoFluxoService.updateCancelar(codigoPedido);
		return ResponseEntity.noContent().build();
	}

}
