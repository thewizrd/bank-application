package com.learning.banking.payload.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ForgotSecurityRequest
 *
 * @author bryan
 * @date Mar 6, 2022-6:34:43 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForgotSecurityRequest {
	@NotBlank
	private String username;
	@NotBlank
	private String securityAnswer;
}
