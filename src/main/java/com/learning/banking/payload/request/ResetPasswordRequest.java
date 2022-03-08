package com.learning.banking.payload.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ResetPasswordRequest
 *
 * @author bryan
 * @date Mar 6, 2022-6:33:04 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequest {
	@NotBlank
	private String username;
	@NotBlank
	private String password;
	@NotBlank
	private String confirmPassword;
}
