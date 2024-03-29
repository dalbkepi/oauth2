package de.handke.gateway.oauth2;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;

@ConfigurationProperties("application.oauth2")
public class AuthProperties {

	/** URI of authorization servers OAuth2 token endpoint. */
	private URI accessTokenUri;

	/** Client ID to provide to authorization server. */
	private String clientId;

	/** Client secret to provide to authorization server */
	private String clientSecret;

	public URI getAccessTokenUri() {
		return accessTokenUri;
	}

	public void setAccessTokenUri(URI accessTokenUri) {
		this.accessTokenUri = accessTokenUri;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
}
