package com.delivery.domain.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.delivery.domain.exception.EntidadeEmUsoException;
import com.delivery.domain.exception.RestauranteNaoEncontradoException;
import com.delivery.domain.model.Cidade;
import com.delivery.domain.model.Cozinha;
import com.delivery.domain.model.FormaPagamento;
import com.delivery.domain.model.Restaurante;
import com.delivery.domain.model.Usuario;
import com.delivery.domain.repository.RestauranteRepository;

@Service
public class RestauranteService {
	
	private static final String RESTAURANTE_EM_USO = "Restaurante cód. %d está em uso.";
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private FormaPagamentoService formaPagamentoService;
	
	@Autowired
	private CidadeService cidadeService;
	
	@Autowired
	private CozinhaService cozinhaService;

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Transactional
	public Restaurante save(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Cozinha cozinha = cozinhaService.findById(cozinhaId);
		
		Long cidadeId = restaurante.getEndereco().getCidade().getId();
		Cidade cidade = cidadeService.findById(cidadeId);
		
		restaurante.setCozinha(cozinha);
		restaurante.getEndereco().setCidade(cidade);
			
		return restauranteRepository.save(restaurante);
	}

	@Transactional
	public void deleteById(Long restauranteId) {
		try {
			restauranteRepository.deleteById(restauranteId);
			restauranteRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new RestauranteNaoEncontradoException(restauranteId);
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(RESTAURANTE_EM_USO, restauranteId));
		}
	}

	public Restaurante findById(Long restauranteId) {
		return restauranteRepository.findById(restauranteId)
				.orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
	}
	
	@Transactional
	public void active(Long restauranteId) {
		Restaurante restauranteCurrent = findById(restauranteId);
		restauranteCurrent.active();
	}
	
	@Transactional
	public void inactive(Long restauranteId) {
		Restaurante restauranteCurrent = findById(restauranteId);
		restauranteCurrent.inactive();
	}
	
	@Transactional
	public void active(List<Long> restaurantesIds) {
		restaurantesIds.forEach(this::active);
	}
	
	@Transactional
	public void inactive(List<Long> restaurantesIds) {
		restaurantesIds.forEach(this::inactive);
	}
	
	@Transactional
	public void open(Long restauranteId) {
	    Restaurante restauranteCurrent = findById(restauranteId);
	    
	    restauranteCurrent.open();
	}

	@Transactional
	public void close(Long restauranteId) {
	    Restaurante restauranteCurrent = findById(restauranteId);
	    
	    restauranteCurrent.close();
	}
	
	@Transactional
	public void removeFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = findById(restauranteId);
		FormaPagamento formaPagamento = formaPagamentoService.findById(formaPagamentoId);
		
		restaurante.removeFormaPagamento(formaPagamento);
	}
	
	@Transactional
	public void addFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = findById(restauranteId);
		FormaPagamento formaPagamento = formaPagamentoService.findById(formaPagamentoId);
		
		restaurante.addFormaPagamento(formaPagamento);
	}
	
	@Transactional
	public void removeResponsavel(Long restauranteId, Long usuarioId) {
		Restaurante restaurante = findById(restauranteId);
		Usuario usuario = usuarioService.findById(usuarioId);
		
		restaurante.removeResponsavel(usuario);
	}
	
	@Transactional
	public void addResponsavel(Long restauranteId, Long usuarioId) {
		Restaurante restaurante = findById(restauranteId);
		Usuario usuario = usuarioService.findById(usuarioId);
		
		restaurante.addResponsavel(usuario);
	}

}
