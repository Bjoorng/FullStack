package com.personal.cafe.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.personal.cafe.entities.Product;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Long>{

    @Query(value = "select p from Product p inner join Category c on c.id = p.category.id where c.id = ?1")
    List<Product> getProductsInCategory(Long catId);
    
    @Query("select p from Product p order by p.fullPrice desc")
    List<Product> filterHighPrice();

    @Query("select p from Product p order by p.fullPrice ")
    List<Product> filterLowPrice();
	
}
