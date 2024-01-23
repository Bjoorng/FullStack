package com.personal.cafe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.personal.cafe.entities.Category;
import com.personal.cafe.exception.MyApiException;
import com.personal.cafe.repositories.CategoryRepository;

@Service
public class CategoryServiceIMPL implements CategoryService{

	@Autowired
    private CategoryRepository categoryRepo;

	@Override
	public List<Category> findAll() {
		return categoryRepo.findAll();
	}

	@Override
	public Category save(Category category) {
		 Category newCategory = new Category();
		 newCategory.setName(category.getName());
	     return categoryRepo.save(newCategory);
	}

	@Override
	public Category findById(Long id) {
		return categoryRepo.findById(id).get();
	}

	@Override
	public Category mod(Category category) {
		Category moddedCategory = null;
        try {
        	moddedCategory= categoryRepo.findById(category.getId()).get();
        	moddedCategory.setName(category.getName());
        }catch (Exception e){
            e.printStackTrace();
        }
        return categoryRepo.save(moddedCategory);
	}

	@Override
	public void delete(Long id) {
		if (categoryRepo.existsById(id)) {
			categoryRepo.deleteById(id);			
		}
		else {
			throw new MyApiException(HttpStatus.BAD_REQUEST, "Category Not Found!");			
		}
	}

	public Category findByName(String name) {
		return categoryRepo.findByName(name);
	}
	
}
