package com.delivery.api.assembler;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.delivery.api.model.RestauranteModel;
import com.delivery.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public RestauranteModel toModel(Restaurante restaurante) {
		return modelMapper.map(restaurante, RestauranteModel.class);
	}
	
	public Page<RestauranteModel> toPageModel(Page<Restaurante> restaurantes) {

		return restaurantes.map(new Function<Restaurante, RestauranteModel>() {
			@Override
			public RestauranteModel apply(Restaurante restaurante) {
				return modelMapper.map(restaurante, RestauranteModel.class);
			}
		});

	}

	public List<RestauranteModel> toListModel(List<Restaurante> restaurantes) {
		return restaurantes.stream()
				.map(restaurante -> toModel(restaurante))
				.collect(Collectors.toList());
	}

}
