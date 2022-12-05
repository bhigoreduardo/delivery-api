package com.delivery.api.assembler;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.delivery.api.model.EstadoModel;
import com.delivery.domain.model.Estado;

@Component
public class EstadoModelAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public EstadoModel toModel(Estado estado) {
		return modelMapper.map(estado, EstadoModel.class);
	}
	
	public Page<EstadoModel> toPageModel(Page<Estado> estados) {

		return estados.map(new Function<Estado, EstadoModel>() {
			@Override
			public EstadoModel apply(Estado estado) {
				return modelMapper.map(estado, EstadoModel.class);
			}
		});

	}

	public List<EstadoModel> toListModel(List<Estado> estados) {
		return estados.stream()
				.map(estado -> toModel(estado))
				.collect(Collectors.toList());
	}

}
