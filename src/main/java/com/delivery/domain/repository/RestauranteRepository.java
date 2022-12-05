package com.delivery.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.delivery.domain.model.Restaurante;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

}
