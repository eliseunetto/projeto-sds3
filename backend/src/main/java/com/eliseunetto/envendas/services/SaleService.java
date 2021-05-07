package com.eliseunetto.envendas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eliseunetto.envendas.dtos.SaleDTO;
import com.eliseunetto.envendas.entities.Sale;
import com.eliseunetto.envendas.repositories.SaleRepository;
import com.eliseunetto.envendas.repositories.SellerRepository;

@Service
public class SaleService {
	
	@Autowired
	private SaleRepository repository;
	
	@Autowired
	private SellerRepository sellerRepository;

	@Transactional(readOnly = true)
	public Page<SaleDTO> findAll(Pageable pageable) {
		sellerRepository.findAll();
		Page<Sale> result = repository.findAll(pageable);
		return result.map(obj -> new SaleDTO(obj));
	}
}
