package com.delivery.api.model.input;

import javax.validation.Valid;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormaPagamentoIdInput {
	
	@Valid
	private Long id;

}
