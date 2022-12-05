package com.delivery.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.delivery.api.assembler.PermissaoModelAssembler;
import com.delivery.api.model.PermissaoModel;
import com.delivery.domain.model.Grupo;
import com.delivery.domain.service.GrupoService;

@RestController
@RequestMapping("/grupos/{grupoId}/permissoes")
public class GrupoPermissaoController {

	@Autowired
	private GrupoService grupoService;

	@Autowired
	private PermissaoModelAssembler permissaoModelAssembler;

	@GetMapping
	public List<PermissaoModel> findAll(@PathVariable Long grupoId) {
		Grupo grupo = grupoService.findById(grupoId);

		List<PermissaoModel> permissoesModel = permissaoModelAssembler
				.toListModel(new ArrayList<>(grupo.getPermissoes()));

		return permissoesModel;
	}

	@DeleteMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> removePermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		grupoService.removePermissao(grupoId, permissaoId);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> addPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		grupoService.addPermissao(grupoId, permissaoId);
		return ResponseEntity.noContent().build();
	}

}
