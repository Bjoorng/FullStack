package com.personal.cafe.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.personal.cafe.dto.LoginDto;
import com.personal.cafe.dto.SignupDto;
import com.personal.cafe.entities.Roles;
import com.personal.cafe.entities.User;
import com.personal.cafe.enums.ERole;
import com.personal.cafe.exception.MyApiException;
import com.personal.cafe.repositories.RoleRepository;
import com.personal.cafe.repositories.UserRepository;
import com.personal.cafe.security.JwtTokenProvider;
import com.personal.cafe.token.VerificationToken;
import com.personal.cafe.token.VerificationTokenRepository;

@Service
public class AuthServiceIMPL implements AuthService {

	private AuthenticationManager authenticationManager;
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private VerificationTokenRepository tokenRepository;

	public AuthServiceIMPL(AuthenticationManager authenticationManager, UserRepository userRepository,
			RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	public String login(LoginDto loginDto) {
		 Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(
	                        loginDto.getUsername(), loginDto.getPassword()));

	        SecurityContextHolder.getContext().setAuthentication(authentication);

	        String token = jwtTokenProvider.generateToken(authentication);

	        return token;
	}

	@Override
	public User signup(SignupDto registerDto) {
		Optional<User> adminUserOptional = userRepository.findByUsername("admin");
        if (adminUserOptional.isEmpty()) {
            User adminUser = new User();
            adminUser.setFirstName("Admin");
            adminUser.setLastName("Admin");
            adminUser.setUsername("admin");
            adminUser.setEmail("admin@example.com");
            adminUser.setIsAuthenticated(true);
            adminUser.setPassword(passwordEncoder.encode("admin"));
            Roles adminRole = roleRepository.findByRoleName(ERole.ADMIN).get();
            adminUser.setRoles(Collections.singleton(adminRole));
            userRepository.save(adminUser);
        }
		if (userRepository.existsByUsername(registerDto.getUsername())) {
			throw new MyApiException(HttpStatus.BAD_REQUEST, "Username is already exists!.");
		}

		if (userRepository.existsByEmail(registerDto.getEmail())) {
			throw new MyApiException(HttpStatus.BAD_REQUEST, "Email is already exists!.");
		}

		User user = new User();
		user.setFirstName(registerDto.getFirstName());
		user.setLastName(registerDto.getLastName());
		user.setUsername(registerDto.getUsername());
		user.setEmail(registerDto.getEmail());
		user.setPhone(registerDto.getPhone());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

		Set<Roles> roles = new HashSet<>();

		if (registerDto.getRoles() != null) {
			registerDto.getRoles().forEach(role -> {
				Roles userRole = roleRepository.findByRoleName(getRole(role)).get();
				roles.add(userRole);
			});
		} else {
			Roles userRole = roleRepository.findByRoleName(ERole.USER).get();
			roles.add(userRole);
		}

		user.setRoles(roles);
		System.out.println(user);
		userRepository.save(user);

		return user;
	}

	public ERole getRole(String role) {
        if (role.equals("ADMIN"))
            return ERole.ADMIN;
        else
            return ERole.USER;
    }
	
	@Override
	public void saveUserVerificationToken(User theUser, String token) {
		VerificationToken verificationToken = new VerificationToken(token, theUser);
		tokenRepository.save(verificationToken);
	}
}
