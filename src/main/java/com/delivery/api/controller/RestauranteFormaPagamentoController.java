package com.delivery.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.delivery.api.assembler.FormaPagamentoModelAssembler;
import com.delivery.api.model.FormaPagamentoModel;
import com.delivery.domain.model.Restaurante;
import com.delivery.domain.service.RestauranteService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoController {

	@Autowired
	private RestauranteService restauranteService;

	@Autowired
	private FormaPagamentoModelAssembler formaPagamentoModelAssembler;

	@GetMapping
	public List<FormaPagamentoModel> findAll(@PathVariable Long restauranteId) {
		Restaurante restaurante = restauranteService.findById(restauranteId);

		List<FormaPagamentoModel> formasPagamentoModel = formaPagamentoModelAssembler
				.toListModel(new ArrayList<>(restaurante.getFormasPagamento()));

		return formasPagamentoModel;
	}

	// @ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{formaPagamentoId}")
	public ResponseEntity<Void> removeFormaPagamento(@PathVariable Long restauranteId,
			@PathVariable Long formaPagamentoId) {
		restauranteService.removeFormaPagamento(restauranteId, formaPagamentoId);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{formaPagamentoId}")
	public ResponseEntity<Void> addFormaPagamento(@PathVariable Long restauranteId,
			@PathVariable Long formaPagamentoId) {
		restauranteService.addFormaPagamento(restauranteId, formaPagamentoId);
		return ResponseEntity.noContent().build();
	}

}
