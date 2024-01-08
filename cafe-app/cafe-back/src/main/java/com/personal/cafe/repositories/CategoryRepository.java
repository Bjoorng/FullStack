package com.personal.cafe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.personal.cafe.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

}
