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

import com.delivery.api.assembler.CozinhaInputDisassembler;
import com.delivery.api.assembler.CozinhaModelAssembler;
import com.delivery.api.model.CozinhaModel;
import com.delivery.api.model.input.CozinhaInput;
import com.delivery.domain.model.Cozinha;
import com.delivery.domain.repository.CozinhaRepository;
import com.delivery.domain.service.CozinhaService;

@RestController
@RequestMapping(path = "/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController {

	@Autowired
	private CozinhaModelAssembler cozinhaModelAssembler;

	@Autowired
	private CozinhaInputDisassembler cozinhaInputDisassembler;

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CozinhaService cozinhaService;

	@GetMapping
	public Page<CozinhaModel> findAll(@PageableDefault(size = 5) Pageable pageable) {
		return cozinhaModelAssembler.toPageModel(cozinhaRepository.findAll(pageable));
	}

	@GetMapping("/{cozinhaId}")
	public CozinhaModel findById(@PathVariable Long cozinhaId) {
		Cozinha cozinha = cozinhaService.findById(cozinhaId);

		return cozinhaModelAssembler.toModel(cozinha);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaModel insert(@RequestBody @Valid CozinhaInput cozinhaInput) {

		Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);

		cozinha = cozinhaService.save(cozinha);

		return cozinhaModelAssembler.toModel(cozinha);
	}

	@PutMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.OK)
	public CozinhaModel update(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaInput cozinhaInput) {
		Cozinha cozinhaCurrent = cozinhaService.findById(cozinhaId);

		cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaCurrent);

		cozinhaCurrent = cozinhaService.save(cozinhaCurrent);

		return cozinhaModelAssembler.toModel(cozinhaCurrent);
	}

	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long cozinhaId) {
		cozinhaService.deleteById(cozinhaId);
	}

}
