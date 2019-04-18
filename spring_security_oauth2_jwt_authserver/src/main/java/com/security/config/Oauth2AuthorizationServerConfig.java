package com.security.config;

import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class Oauth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{

	@Autowired
	DataSource dataSource;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		  converter.setSigningKey("123");
//	    final KeyStoreKeyFactory keyStoreKeyFactory = 
//	      new KeyStoreKeyFactory(new ClassPathResource("mytest.jks"), "mypass".toCharArray());
//	    converter.setKeyPair(keyStoreKeyFactory.getKeyPair("mytest"));
//	    KeyPair k = new KeyPair(publicKey, privateKey)
//	    converter.setKeyPair(keyPair);
		return converter;
	}
	
	
	
	@Bean 
	public TokenStore tokenStore(){
		TokenStore tokenStore = new JwtTokenStore(accessTokenConverter());
		return tokenStore;
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.jdbc(dataSource);
	}
	
	//Giving access to resource server if it is in different server otherwise can comment
	//This is for exposing token api to check token is valid or not
	// http://localhost:8080/oauth/check_token
	@Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) 
      throws Exception {
        oauthServer
          .tokenKeyAccess("permitAll()")
          .checkTokenAccess("isAuthenticated()");
    }
	
	@Bean
	public TokenEnhancer tokenEnhancer() {
	    return new CustomTokenEnhancer();
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
	    tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter()));
		
	    endpoints.tokenStore(tokenStore())
		.tokenEnhancer(tokenEnhancerChain)
		.authenticationManager(authenticationManager)
		.accessTokenConverter(accessTokenConverter());
	}
	
}



/*package com.ggk.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableAuthorizationServer
public class Oauth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{

	@Autowired
	DataSource dataSource;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("as466gf");
        return converter;
	}
	
	@Bean 
	public JdbcTokenStore tokenStore(){
		JdbcTokenStore tokenStore = new JdbcTokenStore(dataSource);
		return tokenStore;
	}
	
//	@Bean 
//	public JdbcTokenStore tokenStore1(){
//		JdbcTokenStore tokenStore = new JdbcTokenStore(dataSource);
//		return tokenStore;
//	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.jdbc(dataSource);
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore())
		.accessTokenConverter(accessTokenConverter())
		.authenticationManager(authenticationManager);
	}
	
}
*/