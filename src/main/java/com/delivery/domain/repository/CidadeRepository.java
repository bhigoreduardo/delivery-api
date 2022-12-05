package com.delivery.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.delivery.domain.model.Cidade;
import com.delivery.domain.model.Estado;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {

	@Query("FROM Cidade c WHERE c.nome = :cidadeNome AND c.estado = :estado")
	public Optional<Cidade> findByNomeAndEstado(String cidadeNome, Estado estado);

}
