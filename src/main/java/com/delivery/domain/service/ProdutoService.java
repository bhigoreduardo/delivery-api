package com.delivery.domain.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.delivery.domain.exception.ProdutoNaoEncontradoException;
import com.delivery.domain.model.Produto;
import com.delivery.domain.model.Restaurante;
import com.delivery.domain.repository.ProdutoRepository;

@Service
public class ProdutoService {
	
	@Autowired
	private RestauranteService restauranteService;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Transactional
	public Produto save(Produto produto) {
		return produtoRepository.save(produto);
	}
	
	public Produto findById(Long produtoId, Long restauranteId) {
		return produtoRepository.findById(produtoId, restauranteId)
				.orElseThrow(() -> new ProdutoNaoEncontradoException(produtoId));
	}
	
	public List<Produto> findByRestaurante(Restaurante restaurante) {
		return produtoRepository.findByRestaurante(restaurante);
	}
	
	@Transactional
	public void active(Long restauranteId, Long produtoId) {
		Restaurante restaurante = restauranteService.findById(restauranteId);
		Optional<Produto> produto = produtoRepository.findById(produtoId, restaurante.getId());
		produto.get().active();
	}
	
	@Transactional
	public void inactive(Long restauranteId, Long produtoId) {
		Restaurante restaurante = restauranteService.findById(restauranteId);
		Optional<Produto> produto = produtoRepository.findById(produtoId, restaurante.getId());
		produto.get().inactive();
	}

}
