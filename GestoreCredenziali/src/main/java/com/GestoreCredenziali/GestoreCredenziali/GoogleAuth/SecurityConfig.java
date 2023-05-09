package com.GestoreCredenziali.GestoreCredenziali.GoogleAuth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	// Metodo di filtro per la sicurezza in cui può essere richiesto o meno il login tramite Google.
	// Per come impostato qui all'URL http://localhost:8085/api/utenti/oauth_login viene richiesto il login con Google,
	// mentre per tutte le altre richieste non viene richiesto il login.
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.csrf().disable()
				.authorizeRequests()
				.antMatchers("/api/utenti/oauth_login")
				.authenticated()
				.anyRequest()
				.permitAll()
				.and()
				.oauth2Login()
				.defaultSuccessUrl("/api/utenti/loginSuccess", true)
				.failureUrl("/api/utenti/loginFailure")
		;
		return http.build();
	}

	@Bean
	public AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository() {
		return new HttpSessionOAuth2AuthorizationRequestRepository();
	}
}
