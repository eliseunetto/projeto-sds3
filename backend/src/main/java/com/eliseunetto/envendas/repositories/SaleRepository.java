package com.eliseunetto.envendas.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eliseunetto.envendas.dtos.SaleSuccessDTO;
import com.eliseunetto.envendas.dtos.SaleSumDTO;
import com.eliseunetto.envendas.entities.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

	@Query("SELECT new com.eliseunetto.envendas.dtos.SaleSumDTO(obj.seller, SUM(obj.amount)) "
			+ " FROM Sale AS obj GROUP BY obj.seller")
	List<SaleSumDTO> amountGroupedBySeller();
	
	@Query("SELECT new com.eliseunetto.envendas.dtos.SaleSuccessDTO(obj.seller, SUM(obj.visited), SUM(obj.deals)) "
			+ " FROM Sale AS obj GROUP BY obj.seller")
	List<SaleSuccessDTO> successGroupedBySeller();
}
