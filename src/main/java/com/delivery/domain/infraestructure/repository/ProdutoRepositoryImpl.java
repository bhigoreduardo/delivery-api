package com.delivery.domain.infraestructure.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.delivery.domain.model.FotoProduto;
import com.delivery.domain.repository.ProdutoRepositoryQueries;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepositoryQueries {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	@Override
	public FotoProduto save(FotoProduto fotoProduto) {
		entityManager.persist(fotoProduto);
		return fotoProduto;
	}
	
	@Override
	public void delete(FotoProduto fotoProduto) {
		entityManager.remove(fotoProduto);
	}

}
