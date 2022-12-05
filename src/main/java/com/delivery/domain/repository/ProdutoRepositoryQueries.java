package com.delivery.domain.repository;

import com.delivery.domain.model.FotoProduto;

public interface ProdutoRepositoryQueries {
	
	public FotoProduto save(FotoProduto fotoProduto);
	
	public void delete(FotoProduto fotoProduto);

}
