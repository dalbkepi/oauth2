package de.handke.authserver.config;

import static java.util.Arrays.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


@Configuration
public class AuthManagerConfig {

	@Bean
	public UserDetailsService userDetailsService() {
		final InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();

		userDetailsManager.createUser(
			new User("admin", passwordEncoder().encode("admin"),
				asList(
					new SimpleGrantedAuthority("ACCOUNT"),
					new SimpleGrantedAuthority("BANK")
				)
			)
		);

		return userDetailsManager;
	}

	/**
	 * Provide BCrypt password encoder.
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
