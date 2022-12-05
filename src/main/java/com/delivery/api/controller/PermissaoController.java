package com.delivery.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.delivery.api.assembler.PermissaoModelAssembler;
import com.delivery.api.model.PermissaoModel;
import com.delivery.domain.model.Permissao;
import com.delivery.domain.repository.PermissaoRepository;
import com.delivery.domain.service.PermissaoService;

@RestController
@RequestMapping(path = "/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissaoController {

	@Autowired
	private PermissaoModelAssembler permissaoModelAssembler;

	@Autowired
	private PermissaoRepository permissaoRepository;

	@Autowired
	private PermissaoService permissaoService;

	@GetMapping
	public Page<PermissaoModel> findAll(@PageableDefault(size = 5) Pageable pageable) {
		return permissaoModelAssembler.toPageModel(permissaoRepository.findAll(pageable));
	}

	@GetMapping("/{permissaoId}")
	public PermissaoModel findById(@PathVariable Long permissaoId) {
		Permissao permissao = permissaoService.findById(permissaoId);

		return permissaoModelAssembler.toModel(permissao);
	}

}
