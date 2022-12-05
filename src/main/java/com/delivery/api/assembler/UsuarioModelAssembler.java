package com.delivery.api.assembler;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.delivery.api.model.UsuarioModel;
import com.delivery.domain.model.Usuario;

@Component
public class UsuarioModelAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public UsuarioModel toModel(Usuario usuario) {
		return modelMapper.map(usuario, UsuarioModel.class);
	}
	
	public Page<UsuarioModel> toPageModel(Page<Usuario> usuarios) {

		return usuarios.map(new Function<Usuario, UsuarioModel>() {
			@Override
			public UsuarioModel apply(Usuario usuario) {
				return modelMapper.map(usuario, UsuarioModel.class);
			}
		});

	}

	public List<UsuarioModel> toListModel(List<Usuario> usuarios) {
		return usuarios.stream()
				.map(usuario -> toModel(usuario))
				.collect(Collectors.toList());
	}

}
