package com.eliseunetto.envendas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eliseunetto.envendas.entities.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

}
