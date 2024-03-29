package com.personal.cafe.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.personal.cafe.dto.ProductDto;
import com.personal.cafe.entities.Product;
import com.personal.cafe.repositories.ProductsRepository;

@Service
public class ProductsServiceIMPL implements ProductsService {

	@Autowired
	private ProductsRepository productRepository;

	@Override
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	@Override
	public Product save(Product product) {
		Product p = new Product();
		p.setProductName(product.getProductName());
		p.setFullPrice(product.getFullPrice());
		p.setDiscount(product.getDiscount());
		p.setQuantity(product.getQuantity());
		p.setSmallDescription(product.getSmallDescription());
		p.setDescription(product.getDescription());
		p.setPicture(product.getPicture());
		p.setVisible(true);
		p.setCategory(product.getCategory());
		productRepository.save(p);
		return p;
	}

	@Override
	public void deleteById(Long id) {
		Product product = productRepository.getById(id);
		productRepository.delete(product);
	}

	@Override
	public Product getById(Long id) {
		Optional<Product> product = productRepository.findById(id);
		return product.orElse(null);
	}
	
	public List<Product> filterPriceLow(){
		return productRepository.filterPriceLow();
	}
	
	public List<Product> filterPriceHigh(){
		return productRepository.filterPriceHigh();
	}

}
