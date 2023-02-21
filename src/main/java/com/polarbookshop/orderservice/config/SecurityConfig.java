package com.polarbookshop.orderservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.savedrequest.NoOpServerRequestCache;


@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {
	private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);
	
	@Bean
	SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws Exception {
		log.info(">>> Applied SecurityFilterChain Bean");
		
		return http
			.authorizeExchange(exchange -> exchange
				.pathMatchers("/actuator/**").permitAll()
				.anyExchange().authenticated()
			)
			.oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt)
			.requestCache(requestCacheSpec ->
				requestCacheSpec.requestCache(NoOpServerRequestCache.getInstance()))
			.csrf(ServerHttpSecurity.CsrfSpec::disable)
			.build();
	}

}
