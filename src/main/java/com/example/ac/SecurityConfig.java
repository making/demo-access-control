package com.example.ac;

import com.example.ac.security.RoleAuthAuthenticationUserDetailsService;
import com.example.ac.security.RoleAuthPreAuthenticationFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests() //
				.anyRequest().authenticated() //
				.and() //
				.csrf().disable() //
				.addFilterAt(preAuthenticatedFilter(),
						AbstractPreAuthenticatedProcessingFilter.class);
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		PreAuthenticatedAuthenticationProvider authenticationProvider = new PreAuthenticatedAuthenticationProvider();
		authenticationProvider.setPreAuthenticatedUserDetailsService(
				authenticationUserDetailsService());
		return authenticationProvider;
	}

	@Bean
	public AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> authenticationUserDetailsService() {
		return new RoleAuthAuthenticationUserDetailsService();
	}

	@Bean
	public RoleAuthPreAuthenticationFilter preAuthenticatedFilter() throws Exception {
		RoleAuthPreAuthenticationFilter preAuthenticatedFilter = new RoleAuthPreAuthenticationFilter();
		preAuthenticatedFilter.setAuthenticationManager(authenticationManager());
		return preAuthenticatedFilter;
	}
}
