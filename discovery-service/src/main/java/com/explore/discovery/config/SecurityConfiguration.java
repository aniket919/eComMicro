package com.explore.discovery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

//@Configuration
//@EnableWebSecurity
public class SecurityConfiguration {
	
	 	//@Bean
	    public UserDetailsService userDetailsService(){

	        UserDetails admin = User.withUsername("aniket")
	                .password(passwordEncoder().encode("admin"))
	                .roles("ADMIN")
	                .build();

	        UserDetails user = User.withUsername("alex")
	                .password(passwordEncoder().encode("1231"))
	                .roles("USER")
	                .build();

	        return new InMemoryUserDetailsManager(admin, user);
	        
	    }
	 	
	 	 @Bean
	     public PasswordEncoder passwordEncoder(){
	          return new BCryptPasswordEncoder();
	     }
	 	 
	 	@Bean
		public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

	        return httpSecurity.csrf().disable()
	            //    .authorizeHttpRequests().requestMatchers("/products/**").authenticated()
	        		.authorizeHttpRequests()
	        		.anyRequest()
	        		.authenticated()
	        		.and()
	        		.formLogin()
	        		.and()
	        		.build();
//	                .and().formLogin()
//	                .and().build();
	    }

}
