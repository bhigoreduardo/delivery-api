package com.delivery.api.assembler;

import java.util.function.Function;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.delivery.api.model.PedidoModel;
import com.delivery.domain.model.Pedido;

@Component
public class PedidoModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public PedidoModel toModel(Pedido pedido) {
		return modelMapper.map(pedido, PedidoModel.class);
	}
	
	public Page<PedidoModel> toPageModel(Page<Pedido> pedidos) {
		return pedidos.map(new Function<Pedido, PedidoModel>() {
			@Override
			public PedidoModel apply(Pedido pedido) {
				return toModel(pedido);
			}
		});
	}

}
