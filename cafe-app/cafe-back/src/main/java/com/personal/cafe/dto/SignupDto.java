package com.personal.cafe.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SignupDto {
	
	private String firstName;
	private String lastName;
    private String username;
    private String email;
    private String phone;
    private String password;
    private Set<String> roles;
	
}
