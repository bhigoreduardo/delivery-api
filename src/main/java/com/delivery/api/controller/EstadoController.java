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

import com.delivery.api.assembler.EstadoInputDisassembler;
import com.delivery.api.assembler.EstadoModelAssembler;
import com.delivery.api.model.EstadoModel;
import com.delivery.api.model.input.EstadoInput;
import com.delivery.domain.model.Estado;
import com.delivery.domain.repository.EstadoRepository;
import com.delivery.domain.service.EstadoService;

@RestController
@RequestMapping(path = "/estados", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoController {

	@Autowired
	private EstadoModelAssembler estadoModelAssembler;

	@Autowired
	private EstadoInputDisassembler estadoInputDisassembler;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private EstadoService estadoService;

	@GetMapping
	public Page<EstadoModel> findAll(@PageableDefault(size = 5) Pageable pageable) {
		return estadoModelAssembler.toPageModel(estadoRepository.findAll(pageable));
	}

	@GetMapping("/{estadoId}")
	public EstadoModel findById(@PathVariable Long estadoId) {
		Estado estado = estadoService.findById(estadoId);

		return estadoModelAssembler.toModel(estado);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoModel insert(@RequestBody @Valid EstadoInput estadoInput) {
		Estado estado = estadoInputDisassembler.toDomainObject(estadoInput);

		estado = estadoService.save(estado);

		return estadoModelAssembler.toModel(estado);
	}

	@PutMapping("/{estadoId}")
	@ResponseStatus(HttpStatus.OK)
	public EstadoModel update(@PathVariable Long estadoId, @RequestBody @Valid EstadoInput estadoInput) {
		Estado estadoCurrent = estadoService.findById(estadoId);

		estadoInputDisassembler.copyToDomainObject(estadoInput, estadoCurrent);

		estadoCurrent = estadoService.save(estadoCurrent);

		return estadoModelAssembler.toModel(estadoCurrent);
	}

	@DeleteMapping("/{estadoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long estadoId) {
		estadoService.deleteById(estadoId);
	}

}
