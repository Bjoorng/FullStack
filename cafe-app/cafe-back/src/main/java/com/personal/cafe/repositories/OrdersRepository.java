package com.personal.cafe.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.personal.cafe.entities.Order;

@Repository
public interface OrdersRepository extends JpaRepository<Order, Long>{

	@Query("select o from Order o where o.user.id = ?1")
    List<Order> findAllByCustomerId(Long id);
	
}
