package com.delivery.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.delivery.api.assembler.ProdutoInputDisassembler;
import com.delivery.api.assembler.ProdutoModelAssembler;
import com.delivery.api.model.ProdutoModel;
import com.delivery.api.model.input.ProdutoInput;
import com.delivery.domain.model.Produto;
import com.delivery.domain.model.Restaurante;
import com.delivery.domain.repository.ProdutoRepository;
import com.delivery.domain.service.ProdutoService;
import com.delivery.domain.service.RestauranteService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private RestauranteService restauranteService;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private ProdutoModelAssembler produtoModelAssembler;

	@Autowired
	private ProdutoInputDisassembler produtoInputDisassembler;

	@GetMapping
	public List<ProdutoModel> findAll(@PathVariable Long restauranteId,
			@RequestParam(required = false) Boolean incluirInativos) {
		Restaurante restaurante = restauranteService.findById(restauranteId);

		List<Produto> produtos;

		if (incluirInativos != null) {
			produtos = produtoRepository.findByRestaurante(restaurante);
		} else {
			produtos = produtoRepository.findAtivosByRestaurante(restaurante);
		}

		return produtoModelAssembler.toListModel(produtos);
	}

	@GetMapping("/{produtoId}")
	public ProdutoModel findById(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		Produto produto = produtoService.findById(produtoId, restauranteId);

		return produtoModelAssembler.toModel(produto);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public ProdutoModel insert(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInput produtoInput) {
		Restaurante restaurante = restauranteService.findById(restauranteId);

		Produto produto = produtoInputDisassembler.toDomainObject(produtoInput);
		produto.setRestaurante(restaurante);

		return produtoModelAssembler.toModel(produtoService.save(produto));
	}

	@PutMapping("/{produtoId}")
	public ProdutoModel update(@PathVariable Long restauranteId, @PathVariable Long produtoId,
			@RequestBody @Valid ProdutoInput produtoInput) {
		Produto produtoCurrent = produtoService.findById(produtoId, restauranteId);

		produtoInputDisassembler.copyToDomainObject(produtoInput, produtoCurrent);

		return produtoModelAssembler.toModel(produtoService.save(produtoCurrent));
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PutMapping("/{produtoId}/ativo")
	public ResponseEntity<Void> active(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		produtoService.active(restauranteId, produtoId);
		return ResponseEntity.noContent().build();
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{produtoId}/inativo")
	public ResponseEntity<Void> inactive(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		produtoService.inactive(restauranteId, produtoId);
		return ResponseEntity.noContent().build();
	}

}
