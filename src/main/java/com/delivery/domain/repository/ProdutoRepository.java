package com.delivery.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.delivery.domain.model.FotoProduto;
import com.delivery.domain.model.Produto;
import com.delivery.domain.model.Restaurante;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>, ProdutoRepositoryQueries {

	@Query("FROM Produto p WHERE restaurante.id = :restauranteId AND id = :produtoId")
	public Optional<Produto> findById(@Param("produtoId") Long produtoId, @Param("restauranteId") Long restauranteId);
	
	public List<Produto> findByRestaurante(Restaurante restaurante);
	
	@Query("FROM Produto p WHERE p.ativo = true and p.restaurante = :restaurante")
	public List<Produto> findAtivosByRestaurante(Restaurante restaurante);
	
	@Query("SELECT f FROM FotoProduto f JOIN f.produto p " +
			"WHERE f.produto.id = :produtoId " +
			"AND p.restaurante.id = :restauranteId")
	Optional<FotoProduto> findFotoById(Long restauranteId, Long produtoId);

}
