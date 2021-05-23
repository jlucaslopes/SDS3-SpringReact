package com.jlucaslopes.dsvendas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jlucaslopes.dsvendas.dto.SaleDTO;
import com.jlucaslopes.dsvendas.dto.SaleSuccessDTO;
import com.jlucaslopes.dsvendas.dto.SaleSumDTO;
import com.jlucaslopes.dsvendas.entities.Sale;
import com.jlucaslopes.dsvendas.repositories.SaleRepository;
import com.jlucaslopes.dsvendas.repositories.SellerRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	@Autowired
	private SellerRepository sellerRepository;
	
	@Transactional(readOnly = true)
	public Page<SaleDTO> findAll(Pageable pageable) {
		//Foi feita a consulta abaixo, para salvar os sellers em memória e não precisar voltar na base para cada consulta.
		sellerRepository.findAll();
		Page<Sale> result = repository.findAll(pageable);
		return result.map(x -> new SaleDTO(x));
	}
	
	@Transactional(readOnly = true)
	public List<SaleSumDTO> amountGroupedBySeller() {
		return repository.amountGroupedBySeller();
	}
	
	@Transactional(readOnly = true)
	public List<SaleSuccessDTO> successGroupedBySeller() {
		return repository.successGroupedBySeller();
	}
}
