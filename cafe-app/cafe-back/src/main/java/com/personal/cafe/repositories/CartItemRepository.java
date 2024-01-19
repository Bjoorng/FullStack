package com.personal.cafe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.personal.cafe.entities.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long>{

}
