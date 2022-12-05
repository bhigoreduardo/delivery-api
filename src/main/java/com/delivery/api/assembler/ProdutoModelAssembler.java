package com.delivery.api.assembler;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.delivery.api.model.ProdutoModel;
import com.delivery.domain.model.Produto;

@Component
public class ProdutoModelAssembler {
	
	@Autowired
	private ModelMapper modelMappper;
	
	public ProdutoModel toModel(Produto produto) {
		return modelMappper.map(produto, ProdutoModel.class);
	}
	
	public Page<ProdutoModel> toPageModel(Page<Produto> produtos) {
		return produtos.map(new Function<Produto, ProdutoModel>() {
			@Override
			public ProdutoModel apply(Produto produto) {
				return modelMappper.map(produto, ProdutoModel.class);
			}
		});
	}
	
	public List<ProdutoModel> toListModel(List<Produto> produtos) {
		return produtos.stream()
				.map(produto -> toModel(produto))
				.collect(Collectors.toList());
	}

}
