package com.eliseunetto.envendas.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eliseunetto.envendas.dtos.SellerDTO;
import com.eliseunetto.envendas.entities.Seller;
import com.eliseunetto.envendas.repositories.SellerRepository;

@Service
public class SellerService {
	
	@Autowired
	private SellerRepository repository;

	public List<SellerDTO> findAll() {
		List<Seller> result = repository.findAll();
		return result.stream().map(obj -> new SellerDTO(obj)).collect(Collectors.toList());
	}
}
