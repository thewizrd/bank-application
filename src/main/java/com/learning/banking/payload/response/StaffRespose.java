package com.learning.banking.payload.response;

import com.learning.banking.enums.CustomerStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffRespose {
	private Long staffId;
	private String staffUserName;
	private String staffName;
	private CustomerStatus status;
}
