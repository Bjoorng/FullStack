package com.personal.cafe.service;

import com.personal.cafe.dto.LoginDto;
import com.personal.cafe.dto.SignupDto;
import com.personal.cafe.entities.User;

public interface AuthService {

	String login(LoginDto loginDto);

	User signup(SignupDto registerDto);

	void saveUserVerificationToken(User theUser, String verificationToken);

}
