package com.delivery.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.delivery.api.assembler.UsuarioInputDisassembler;
import com.delivery.api.assembler.UsuarioModelAssembler;
import com.delivery.api.model.UsuarioModel;
import com.delivery.api.model.input.SenhaInput;
import com.delivery.api.model.input.UsuarioComSenhaInput;
import com.delivery.api.model.input.UsuarioInput;
import com.delivery.domain.model.Usuario;
import com.delivery.domain.repository.UsuarioRepository;
import com.delivery.domain.service.UsuarioService;

@RestController
@RequestMapping(path = "/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController {

	@Autowired
	private UsuarioModelAssembler usuarioModelAssembler;

	@Autowired
	private UsuarioInputDisassembler usuarioInputDisassembler;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping
	public Page<UsuarioModel> findAll(@PageableDefault(size = 5) Pageable pageable) {
		return usuarioModelAssembler.toPageModel(usuarioRepository.findAll(pageable));
	}

	@GetMapping("/{usuarioId}")
	public UsuarioModel findById(@PathVariable Long usuarioId) {
		Usuario usuario = usuarioService.findById(usuarioId);

		return usuarioModelAssembler.toModel(usuario);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioModel insert(@RequestBody @Valid UsuarioComSenhaInput usuarioInput) {

		Usuario usuario = usuarioInputDisassembler.toDomainObject(usuarioInput);

		usuario = usuarioService.save(usuario);

		return usuarioModelAssembler.toModel(usuario);
	}

	@PutMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.OK)
	public UsuarioModel update(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioInput usuarioInput) {
		Usuario usuarioCurrent = usuarioService.findById(usuarioId);

		usuarioInputDisassembler.copyToDomainObject(usuarioInput, usuarioCurrent);

		usuarioCurrent = usuarioService.save(usuarioCurrent);

		return usuarioModelAssembler.toModel(usuarioCurrent);
	}
	
	@PutMapping("/{usuarioId}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInput senha) {
        usuarioService.setSenha(usuarioId, senha.getSenhaAtual(), senha.getNovaSenha());
    }

}
