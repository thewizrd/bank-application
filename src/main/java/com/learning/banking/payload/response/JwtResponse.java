package com.learning.banking.payload.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JwtResponse
 *
 * @author bryan
 * @date Feb 24, 2022-4:41:24 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
	private String token;
    private final String type = "Bearer";
    private Long id;
    private String username;
    private List<String> roles;
}
