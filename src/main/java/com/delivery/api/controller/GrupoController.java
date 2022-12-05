package com.delivery.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.delivery.api.assembler.GrupoInputDisassembler;
import com.delivery.api.assembler.GrupoModelAssembler;
import com.delivery.api.model.GrupoModel;
import com.delivery.api.model.input.GrupoInput;
import com.delivery.domain.model.Grupo;
import com.delivery.domain.repository.GrupoRepository;
import com.delivery.domain.service.GrupoService;

@RestController
@RequestMapping(path = "/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoController {

	@Autowired
	private GrupoModelAssembler grupoModelAssembler;

	@Autowired
	private GrupoInputDisassembler grupoInputDisassembler;

	@Autowired
	private GrupoRepository grupoRepository;

	@Autowired
	private GrupoService grupoService;

	@GetMapping
	public Page<GrupoModel> findAll(@PageableDefault(size = 5) Pageable pageable) {
		return grupoModelAssembler.toPageModel(grupoRepository.findAll(pageable));
	}

	@GetMapping("/{grupoId}")
	public GrupoModel findById(@PathVariable Long grupoId) {
		Grupo grupo = grupoService.findById(grupoId);

		return grupoModelAssembler.toModel(grupo);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoModel insert(@RequestBody @Valid GrupoInput grupoInput) {

		Grupo grupo = grupoInputDisassembler.toDomainObject(grupoInput);

		grupo = grupoService.save(grupo);

		return grupoModelAssembler.toModel(grupo);
	}

	@PutMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.OK)
	public GrupoModel update(@PathVariable Long grupoId, @RequestBody @Valid GrupoInput grupoInput) {
		Grupo grupoCurrent = grupoService.findById(grupoId);

		grupoInputDisassembler.copyToDomainObject(grupoInput, grupoCurrent);

		grupoCurrent = grupoService.save(grupoCurrent);

		return grupoModelAssembler.toModel(grupoCurrent);
	}

	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long grupoId) {
		grupoService.deleteById(grupoId);
	}

}
