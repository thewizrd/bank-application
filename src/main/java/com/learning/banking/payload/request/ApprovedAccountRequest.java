package com.learning.banking.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApprovedAccountRequest {
	@Positive
	@NotNull
	private Long accountNumber;

	@NotBlank
	private String staffUserName;
	@NotBlank
	private String approved;
}
