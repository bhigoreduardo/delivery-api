package com.delivery.api.assembler;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.delivery.api.model.CidadeModel;
import com.delivery.domain.model.Cidade;

@Component
public class CidadeModelAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public CidadeModel toModel(Cidade cidade) {
		return modelMapper.map(cidade, CidadeModel.class);
	}
	
	public Page<CidadeModel> toPageModel(Page<Cidade> cidades) {

		return cidades.map(new Function<Cidade, CidadeModel>() {
			@Override
			public CidadeModel apply(Cidade cidade) {
				return modelMapper.map(cidade, CidadeModel.class);
			}
		});

	}

	public List<CidadeModel> toListModel(List<Cidade> cidades) {
		return cidades.stream()
				.map(cidade -> toModel(cidade))
				.collect(Collectors.toList());
	}

}
