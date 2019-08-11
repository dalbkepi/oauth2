package de.handke.accountservice.configuration;

import static org.springframework.http.HttpMethod.*;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(securedEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
			.authorizeRequests()
			.antMatchers(GET, "/accounts").permitAll()
			.antMatchers(GET, "/account/**").hasAnyAuthority("ACCOUNT", "GET_ACCOUNT_DETAILS")
			.antMatchers(POST, "/account/**").hasAuthority("ACCOUNT")
			.requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
			.anyRequest().denyAll();
		// @formatter:on
	}

	/**
	 * Avoid creating default user, because we are using auth-server
	 */
	@Bean
	public UserDetailsService userDetailsService() {
		return new InMemoryUserDetailsManager();
	}
}
