package com.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class SpringSecurityOauth2JwtAuthserverApplication {

	/*
	 * This application using following configuration
	 * 1) Token Enhancer to add extra claims
	 * 2) Symmetric key (123) to encode the jwt at auth server and decode the jwt at resource server
	 
	 */
	
	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityOauth2JwtAuthserverApplication.class, args);
	}

}
