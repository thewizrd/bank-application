package com.learning.banking.payload.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;

import com.learning.banking.enums.CustomerStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponseFromStaff {
	private Long id;
	@NotBlank
	private String username;
	private String fullname;
	@Enumerated(EnumType.STRING)
	private CustomerStatus customerStatus;
	private LocalDateTime createDate;
}
