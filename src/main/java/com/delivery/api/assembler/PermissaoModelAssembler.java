package com.delivery.api.assembler;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.delivery.api.model.PermissaoModel;
import com.delivery.domain.model.Permissao;

@Component
public class PermissaoModelAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public PermissaoModel toModel(Permissao permissao) {
		return modelMapper.map(permissao, PermissaoModel.class);
	}
	
	public Page<PermissaoModel> toPageModel(Page<Permissao> permissoes) {

		return permissoes.map(new Function<Permissao, PermissaoModel>() {
			@Override
			public PermissaoModel apply(Permissao permissao) {
				return modelMapper.map(permissao, PermissaoModel.class);
			}
		});

	}

	public List<PermissaoModel> toListModel(List<Permissao> permissoes) {
		return permissoes.stream()
				.map(permissao -> toModel(permissao))
				.collect(Collectors.toList());
	}

}
