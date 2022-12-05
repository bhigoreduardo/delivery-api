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

import com.delivery.api.assembler.UsuarioModelAssembler;
import com.delivery.api.model.UsuarioModel;
import com.delivery.domain.model.Restaurante;
import com.delivery.domain.service.RestauranteService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioResponsavelController {

	@Autowired
	private RestauranteService restauranteService;

	@Autowired
	private UsuarioModelAssembler usuarioModelAssembler;

	@GetMapping
	public List<UsuarioModel> findAll(@PathVariable Long restauranteId) {
		Restaurante restaurante = restauranteService.findById(restauranteId);

		List<UsuarioModel> usuariosModel = usuarioModelAssembler
				.toListModel(new ArrayList<>(restaurante.getResponsaveis()));

		return usuariosModel;
	}

	@DeleteMapping("/{usuarioId}")
	public ResponseEntity<Void> removeResponsavel(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		restauranteService.removeResponsavel(restauranteId, usuarioId);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{usuarioId}")
	public ResponseEntity<Void> addResponsavel(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		restauranteService.addResponsavel(restauranteId, usuarioId);		
		return ResponseEntity.noContent().build();
	}

}
