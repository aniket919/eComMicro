package com.explore.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

// spring cloud gateway project is based on spring-web-flux project, not on spring-web-mvc,
// hence we need to use @EnableWebFluxSecurity annotation here.

//@Configuration
//@EnableWebFluxSecurity
public class SecurityConfig {

//	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {
		
		serverHttpSecurity.csrf()
							.disable()
							.authorizeExchange(exchange -> exchange
															.pathMatchers("/eureka/**")
															.permitAll()					// till here, we can allow access to eureka for static resources without authentication.
															.anyExchange()					// any other requests patterns specified in pathmatchers, must be authenticated.
															.authenticated())
							
							// now we are going to enable the resourceServer capabilities in our API gateway.
							// in this resourceServer, we also need to enable JWT support
							.oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt);
	
		
		return serverHttpSecurity.build();
		
		
		
	}
 
}
