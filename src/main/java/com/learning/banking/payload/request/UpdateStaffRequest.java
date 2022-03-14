package com.learning.banking.payload.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.learning.banking.enums.CustomerStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStaffRequest {
	@Positive
	@NotNull
	private Long staffId;
	@NotNull
	private CustomerStatus status;
}
