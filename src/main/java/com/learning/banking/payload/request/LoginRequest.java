package com.learning.banking.payload.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * LoginRequest
 *
 * @author bryan
 * @date Mar 4, 2022-5:25:14 PM
 */
@Data
public class LoginRequest {
	@NotBlank
	private String username;
	@NotBlank
	private String password;
}
