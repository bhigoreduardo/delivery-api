package com.delivery.api.assembler;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.delivery.api.model.FormaPagamentoModel;
import com.delivery.domain.model.FormaPagamento;

@Component
public class FormaPagamentoModelAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public FormaPagamentoModel toModel(FormaPagamento formaPagamento) {
		return modelMapper.map(formaPagamento, FormaPagamentoModel.class);
	}
	
	public Page<FormaPagamentoModel> toPageModel(Page<FormaPagamento> formasPagamento) {

		return formasPagamento.map(new Function<FormaPagamento, FormaPagamentoModel>() {
			@Override
			public FormaPagamentoModel apply(FormaPagamento formaPagamento) {
				return modelMapper.map(formaPagamento, FormaPagamentoModel.class);
			}
		});

	}

	public List<FormaPagamentoModel> toListModel(List<FormaPagamento> formasPagamento) {
		return formasPagamento.stream()
				.map(formaPagamento -> toModel(formaPagamento))
				.collect(Collectors.toList());
	}

}
