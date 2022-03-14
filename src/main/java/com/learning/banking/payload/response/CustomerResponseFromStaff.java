package com.learning.banking.payload.response;

import java.time.LocalDateTime;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
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
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createDate;
}
