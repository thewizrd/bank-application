package com.learning.banking.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * GetCustomerQandAResponse
 *
 * @author bryan
 * @date Mar 8, 2022-5:40:22 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetCustomerQandAResponse {
	private String securityQuestion;
	private String securityAnswer;
}
