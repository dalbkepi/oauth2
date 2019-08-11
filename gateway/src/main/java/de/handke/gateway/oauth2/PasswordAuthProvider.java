package de.handke.gateway.oauth2;

import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;


public class PasswordAuthProvider implements AuthenticationProvider {

	private RestTemplate restTemplate;
	private final AuthProperties properties;

	public PasswordAuthProvider(AuthProperties properties, RestTemplate restTemplate) {
		this.properties = properties;
		this.restTemplate = restTemplate;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		if (!(authentication instanceof UsernamePasswordAuthenticationToken)) {
			return null;
		}

		final UsernamePasswordAuthenticationToken usernamePassword = (UsernamePasswordAuthenticationToken) authentication;

		final String username = (String) usernamePassword.getPrincipal();
		final String password = (String) usernamePassword.getCredentials();

		final MultiValueMap<String, String> formFields = new LinkedMultiValueMap<>();
		formFields.add("grant_type", "password");
		formFields.add("username", username);
		formFields.add("password", password);

		final byte[] clientCredentialsBytes = (properties.getClientId() + ":" + properties.getClientSecret()).getBytes(StandardCharsets.US_ASCII);
		final String clientCredentialsEncoded = Base64.getEncoder().encodeToString(clientCredentialsBytes);

		final RequestEntity request = RequestEntity
			.post(properties.getAccessTokenUri())
			.header(HttpHeaders.AUTHORIZATION, "Basic " + clientCredentialsEncoded)
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			.body(formFields);

		final ResponseEntity<OAuth2AccessToken> response = restTemplate.exchange(request, OAuth2AccessToken.class);

		if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
			throw new InsufficientAuthenticationException("Authorization failed");
		} else if (response.getStatusCode() != HttpStatus.OK) {
			throw new AuthenticationServiceException("Authorization failed");
		}

		final OAuth2AccessToken oAuth2AccessToken = response.getBody();

		return new AccessTokenAuth(oAuth2AccessToken, authentication);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
