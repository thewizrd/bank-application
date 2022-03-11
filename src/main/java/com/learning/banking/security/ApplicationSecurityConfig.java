package com.learning.banking.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.learning.banking.security.jwt.AuthEntryPointJwt;
import com.learning.banking.security.jwt.AuthTokenFilter;
import com.learning.banking.security.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity // make sure that security env is enabled
@EnableGlobalMethodSecurity(prePostEnabled = true) // allow the mutiple endpoint
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsServiceImpl userDetailsServiceImpl;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	// to have customized object as per the req
	@Bean // didnot mark AuthTokenFilter component
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManager() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManager();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwrodEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors()
			.and()
			.csrf()
				.disable()// disable cors and csrf
			.exceptionHandling()
				.authenticationEntryPoint(unauthorizedHandler)
			.and()
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests()
				.antMatchers("/api/customer/register", "/api/customer/authenticate")
					.permitAll()
				.antMatchers("/api/admin/authenticate")
					.permitAll()
				.antMatchers("/api/staff/authenticate")
					.permitAll()
				.antMatchers("/api/test/**")
					.permitAll()
				.anyRequest()
					.authenticated()
			.and()
			.httpBasic();

		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	public PasswordEncoder passwrodEncoder() {
		return new BCryptPasswordEncoder(12);
	}
}
