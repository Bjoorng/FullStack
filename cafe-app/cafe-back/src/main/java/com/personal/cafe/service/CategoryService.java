package com.personal.cafe.service;

import java.util.List;

import com.personal.cafe.entities.Category;

public interface CategoryService {
	
	List<Category>findAll();
	Category save(Category category);
	Category findById(Long id);
	Category mod(Category category);
	void delete(Long id);
	
	Category findByName(String name);
}
