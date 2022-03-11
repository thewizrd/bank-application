package com.learning.banking.payload.request;

import com.learning.banking.enums.CustomerStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStaffRequest {
	private Long staffId;
	private CustomerStatus status;
}
