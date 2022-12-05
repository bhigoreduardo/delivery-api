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

import com.delivery.api.assembler.CidadeInputDisassembler;
import com.delivery.api.assembler.CidadeModelAssembler;
import com.delivery.api.model.CidadeModel;
import com.delivery.api.model.input.CidadeInput;
import com.delivery.domain.exception.EstadoNaoEncontradoException;
import com.delivery.domain.exception.NegocioException;
import com.delivery.domain.model.Cidade;
import com.delivery.domain.repository.CidadeRepository;
import com.delivery.domain.service.CidadeService;

@RestController
@RequestMapping(path = "/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController {

	@Autowired
	private CidadeModelAssembler cidadeModelAssembler;

	@Autowired
	private CidadeInputDisassembler cidadeInputDisassembler;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CidadeService cidadeService;

	@GetMapping
	public Page<CidadeModel> findAll(@PageableDefault(size = 5) Pageable pageable) {
		return cidadeModelAssembler.toPageModel(cidadeRepository.findAll(pageable));
	}

	@GetMapping("/{cidadeId}")
	public CidadeModel findById(@PathVariable Long cidadeId) {
		Cidade cidade = cidadeService.findById(cidadeId);

		return cidadeModelAssembler.toModel(cidade);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeModel insert(@RequestBody @Valid CidadeInput cidadeInput) {
		try {
			Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
	
			cidade = cidadeService.save(cidade);
	
			return cidadeModelAssembler.toModel(cidade);
		} catch (EstadoNaoEncontradoException ex) {
			throw new NegocioException(ex.getMessage(), ex);
		}
	}

	@PutMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.OK)
	public CidadeModel update(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInput cidadeInput) {
		try {
			Cidade cidadeCurrent = cidadeService.findById(cidadeId);
		
			cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeCurrent);
		
			cidadeCurrent = cidadeService.save(cidadeCurrent);
		
			return cidadeModelAssembler.toModel(cidadeCurrent);
		} catch (EstadoNaoEncontradoException ex) {
			throw new NegocioException(ex.getMessage(), ex);
		}
	}

	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long cidadeId) {
		cidadeService.deleteById(cidadeId);
	}

}
