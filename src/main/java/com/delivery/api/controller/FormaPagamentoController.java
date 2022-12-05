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

import com.delivery.api.assembler.FormaPagamentoInputDisassembler;
import com.delivery.api.assembler.FormaPagamentoModelAssembler;
import com.delivery.api.model.FormaPagamentoModel;
import com.delivery.api.model.input.FormaPagamentoInput;
import com.delivery.domain.model.FormaPagamento;
import com.delivery.domain.repository.FormaPagamentoRepository;
import com.delivery.domain.service.FormaPagamentoService;

@RestController
@RequestMapping(path = "/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class FormaPagamentoController {

	@Autowired
	private FormaPagamentoModelAssembler formaPagamentoModelAssembler;

	@Autowired
	private FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;

	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;

	@Autowired
	private FormaPagamentoService formaPagamentoService;

	@GetMapping
	public Page<FormaPagamentoModel> findAll(@PageableDefault(size = 5) Pageable pageable) {
		return formaPagamentoModelAssembler.toPageModel(formaPagamentoRepository.findAll(pageable));
	}

	@GetMapping("/{formaPagamentoId}")
	public FormaPagamentoModel findById(@PathVariable Long formaPagamentoId) {
		FormaPagamento formaPagamento = formaPagamentoService.findById(formaPagamentoId);

		return formaPagamentoModelAssembler.toModel(formaPagamento);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamentoModel insert(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
		FormaPagamento formaPagamento = formaPagamentoInputDisassembler.toDomainObject(formaPagamentoInput);

		formaPagamento = formaPagamentoService.save(formaPagamento);

		return formaPagamentoModelAssembler.toModel(formaPagamento);
	}

	@PutMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.OK)
	public FormaPagamentoModel update(@PathVariable Long formaPagamentoId, @RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
		FormaPagamento formaPagamentoCurrent = formaPagamentoService.findById(formaPagamentoId);

		formaPagamentoInputDisassembler.copyToDomainObject(formaPagamentoInput, formaPagamentoCurrent);

		formaPagamentoCurrent = formaPagamentoService.save(formaPagamentoCurrent);

		return formaPagamentoModelAssembler.toModel(formaPagamentoCurrent);
	}

	@DeleteMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long formaPagamentoId) {
		formaPagamentoService.deleteById(formaPagamentoId);
	}

}
