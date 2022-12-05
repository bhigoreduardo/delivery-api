package com.delivery.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.delivery.api.assembler.FotoProdutoModelAssembler;
import com.delivery.api.model.FotoProdutoModel;
import com.delivery.domain.model.FotoProduto;
import com.delivery.domain.service.FotoProdutoService;

@RestController
@RequestMapping("/restaurantes/{restuaranteId}/produtos/{produtoId}/fotos")
public class RestauranteProdutoFotoController {

	@Autowired
	private FotoProdutoService fotoProdutoService;

	@Autowired
	private FotoProdutoModelAssembler fotoProdutoModelAssembler;

	@GetMapping
	public FotoProdutoModel findById(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		FotoProduto fotoProduto = fotoProdutoService.findById(restauranteId, produtoId);

		return fotoProdutoModelAssembler.toModel(fotoProduto);
	}

}
