package de.handke.bankinfoservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;


@SpringBootApplication
@EnableDiscoveryClient
public class BankInfoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankInfoServiceApplication.class, args);
	}


	@Bean
	@ConfigurationProperties("security.oauth2.client")
	protected ClientCredentialsResourceDetails oAuthDetails() {
		return new ClientCredentialsResourceDetails();
	}

	@Bean
	@LoadBalanced
	public OAuth2RestTemplate oAuth2RestTemplate() {
		OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(oAuthDetails());
		oAuth2RestTemplate.getAccessToken();
		return oAuth2RestTemplate;
	}

}
