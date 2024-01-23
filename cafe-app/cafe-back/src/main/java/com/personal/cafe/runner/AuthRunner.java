package com.personal.cafe.runner;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.personal.cafe.entities.Roles;
import com.personal.cafe.enums.ERole;
import com.personal.cafe.repositories.RoleRepository;
import com.personal.cafe.repositories.UserRepository;
import com.personal.cafe.service.AuthService;

@Component
public class AuthRunner implements ApplicationRunner{
	
	@Autowired RoleRepository roleRepository;
	@Autowired UserRepository userRepository;
	@Autowired PasswordEncoder passwordEncoder;
	@Autowired AuthService authService;
	
	private Set<Roles> adminRole;
	private Set<Roles> userRole;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("Run auth...");
		setRoleDefault(); 
	}
	
	private void setRoleDefault() {
		Optional<Roles> adminOptional = roleRepository.findByRoleName(ERole.ADMIN);
		if (!adminOptional.isPresent()) {
			Roles admin = new Roles();
			admin.setRoleName(ERole.ADMIN);
			roleRepository.save(admin);
		}
		
		Optional<Roles> userOptional = roleRepository.findByRoleName(ERole.USER);
		if (!userOptional.isPresent()) {
			Roles user = new Roles();
			user.setRoleName(ERole.USER);
			roleRepository.save(user);
		}

		adminRole = new HashSet<Roles>();
		adminRole.add(adminOptional.orElse(null));
		adminRole.add(userOptional.orElse(null));
		
		userRole = new HashSet<Roles>();
		userRole.add(userOptional.orElse(null));
	}
	
}
