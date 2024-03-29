package com.personal.cafe.payload;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class JwtAuthResponse {

	private String username;
    private String accessToken;
    private String tokenType = "Bearer";
    private Set<String> roles;
	
}
