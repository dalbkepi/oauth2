package de.handke.gateway.configuration;

import static java.util.Collections.*;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.client.RestTemplate;

import de.handke.gateway.oauth2.AuthProperties;
import de.handke.gateway.oauth2.PasswordAuthProvider;


@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(AuthProperties.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final AuthProperties properties;


	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}


	@Autowired
	public SecurityConfig(AuthProperties properties) {
		this.properties = properties;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		// Configure formbased login using /login.
		// Only use simple HTTP status codes as response instead of redirects to utilize usage by single page webapp
		// (e.g. using http fetch api).
		http
			.formLogin()
			.loginPage("/login")
			.successHandler((request, reponse, authentication) -> reponse.setStatus(HttpStatus.SC_OK))
			.failureHandler((request, reponse, exception) -> reponse.setStatus(HttpStatus.SC_FORBIDDEN))
			// Configure logout using /logout
			.and()
			.logout()
			.logoutUrl("/logout")
			.logoutSuccessHandler((request, reponse, authentication) -> reponse.setStatus(HttpStatus.SC_OK))
			// To focus only on OAuth and login handling, CSRF is disabled here.
			// For production use, CSRF should also be considered.
			.and()
			.csrf()
			.disable()
		;
		// @formatter:on
	}

	/**
	 * Configure our custom authentication provider to forward login to oauth2
	 * authorization server.
	 */
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() {
		return new ProviderManager(singletonList(new PasswordAuthProvider(properties, restTemplate())));
	}
}
