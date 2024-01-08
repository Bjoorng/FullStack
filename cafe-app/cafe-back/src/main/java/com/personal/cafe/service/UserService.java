package com.personal.cafe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.personal.cafe.entities.User;
import com.personal.cafe.exception.MyApiException;
import com.personal.cafe.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	public User getById(Long id) {
		if (userRepository.existsById(id)) {
			return userRepository.findById(id).get();
		} else {
			throw new MyApiException(HttpStatus.BAD_REQUEST, "User Not Found!");
		}
	}

	public User getByUsername(String username) {
		if (userRepository.existsByUsername(username)) {
			return userRepository.findByUsername(username).get();
		} else {
			throw new MyApiException(HttpStatus.BAD_REQUEST, "Username Not Found!");
		}
	}

	public User getByEmail(String Email) {
		if (userRepository.existsByEmail(Email)) {
			return userRepository.findByEmail(Email).get();
		} else {
			throw new MyApiException(HttpStatus.BAD_REQUEST, "No User with This Email!");
		}
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public void deleteById(Long id) {
		if (userRepository.existsById(id)) {
			userRepository.deleteById(id);
		} else {
			throw new MyApiException(HttpStatus.BAD_REQUEST, "Couldn't Delete The User. Check The Data!");
		}
	}
}
