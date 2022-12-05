package com.delivery.api.assembler;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.delivery.api.model.CozinhaModel;
import com.delivery.domain.model.Cozinha;

@Component
public class CozinhaModelAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public CozinhaModel toModel(Cozinha cozinha) {
		return modelMapper.map(cozinha, CozinhaModel.class);
	}
	
	public Page<CozinhaModel> toPageModel(Page<Cozinha> cozinhas) {

		return cozinhas.map(new Function<Cozinha, CozinhaModel>() {
			@Override
			public CozinhaModel apply(Cozinha cozinha) {
				return modelMapper.map(cozinha, CozinhaModel.class);
			}
		});

	}

	public List<CozinhaModel> toListModel(List<Cozinha> cozinhas) {
		return cozinhas.stream()
				.map(cozinha -> toModel(cozinha))
				.collect(Collectors.toList());
	}

}
