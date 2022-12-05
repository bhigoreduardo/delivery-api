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

import com.delivery.api.assembler.GrupoModelAssembler;
import com.delivery.api.model.GrupoModel;
import com.delivery.domain.model.Usuario;
import com.delivery.domain.service.UsuarioService;

@RestController
@RequestMapping("/usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private GrupoModelAssembler grupoModelAssembler;

	@GetMapping
	public List<GrupoModel> findAll(@PathVariable Long usuarioId) {
		Usuario usuario = usuarioService.findById(usuarioId);

		List<GrupoModel> gruposModel = grupoModelAssembler.toListModel(new ArrayList<>(usuario.getGrupos()));

		return gruposModel;
	}

	@DeleteMapping("/{grupoId}")
	public ResponseEntity<Void> removeGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		usuarioService.removeGrupo(usuarioId, grupoId);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{grupoId}")
	public ResponseEntity<Void> addGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		usuarioService.addGrupo(usuarioId, grupoId);
		return ResponseEntity.noContent().build();
	}

}
