package com.learning.banking.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ApiMessage
 *
 * @author bryan
 * @date Feb 21, 2022-5:15:09 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiMessage {
	private String message;
}
