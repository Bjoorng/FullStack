package com.personal.cafe.service;

import java.util.List;

import com.personal.cafe.dto.ProductDto;
import com.personal.cafe.entities.Product;

public interface ProductsService {
	List<Product> findAll();
    Product save(Product product);
    void deleteById(Long id);
    Product getById(Long id);
}
