package com.personal.cafe.controller;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.personal.cafe.dto.LoginDto;
import com.personal.cafe.dto.SignupDto;
import com.personal.cafe.entities.User;
import com.personal.cafe.payload.JwtAuthResponse;
import com.personal.cafe.security.CustomUserDetailsService;
import com.personal.cafe.service.AuthService;
import com.personal.cafe.token.VerificationTokenRepository;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

	@Autowired
	private VerificationTokenRepository tokenRepository;

	@Autowired
	private AuthService authService;
	
	@Autowired
	private CustomUserDetailsService userDetailsService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping(value = { "/login" })
	public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto) {

		String token = authService.login(loginDto);
	    UserDetails userDetails = userDetailsService.loadUserByUsername(loginDto.getUsername());

		
		Set<String> userRoles = userDetails.getAuthorities()
	            .stream()
	            .map(GrantedAuthority::getAuthority)
	            .collect(Collectors.toSet());
		
		JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
		jwtAuthResponse.setUsername(loginDto.getUsername());
		jwtAuthResponse.setAccessToken(token);
		jwtAuthResponse.setRoles(userRoles);
		
		return ResponseEntity.ok(jwtAuthResponse);
	}

	@PostMapping(value = { "/signup" })
	public ResponseEntity<User> signup(@RequestBody SignupDto signupDto) {
		User response = authService.signup(signupDto);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

}
