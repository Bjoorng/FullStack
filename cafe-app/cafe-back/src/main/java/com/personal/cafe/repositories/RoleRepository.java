package com.personal.cafe.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.personal.cafe.entities.Roles;
import com.personal.cafe.enums.ERole;

public interface RoleRepository extends JpaRepository<Roles, Long> {
	
	Optional<Roles> findByRoleName(ERole roleName);
	
}
