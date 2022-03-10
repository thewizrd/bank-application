package com.learning.banking.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * PasswordEncoderConfig
 *
 * @author bryan
 * @date Mar 9, 2022-5:28:11 PM
 */
@Configuration
/* NOTE: Just for testing; this will be replaced */
public class PasswordEncoderConfig {
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}
}