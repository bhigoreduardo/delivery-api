package com.delivery.api.assembler;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.delivery.api.model.GrupoModel;
import com.delivery.domain.model.Grupo;

@Component
public class GrupoModelAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public GrupoModel toModel(Grupo grupo) {
		return modelMapper.map(grupo, GrupoModel.class);
	}
	
	public Page<GrupoModel> toPageModel(Page<Grupo> grupos) {

		return grupos.map(new Function<Grupo, GrupoModel>() {
			@Override
			public GrupoModel apply(Grupo grupo) {
				return modelMapper.map(grupo, GrupoModel.class);
			}
		});

	}

	public List<GrupoModel> toListModel(List<Grupo> grupos) {
		return grupos.stream()
				.map(grupo -> toModel(grupo))
				.collect(Collectors.toList());
	}

}
