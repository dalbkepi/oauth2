package de.handke.bankinfoservice.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import de.handke.bankinfoservice.controller.dto.AccountDTO;
import de.handke.bankinfoservice.controller.dto.BankDTO;
import de.handke.bankinfoservice.entities.BankInfo;
import de.handke.bankinfoservice.service.BankInfoService;


@RestController
public class BankController {

	@Autowired
	private BankInfoService bankInfoService;

	@Autowired
	private OAuth2RestTemplate oAuth2RestTemplate;

	@Value("${account-service.account-url}")
	private String accountUrl;

	@GetMapping(value = "/bank/{accountId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BankDTO> getBankInformation(@PathVariable(value = "accountId") Long accountId) throws URISyntaxException {

		Optional<BankInfo> bankInfo = bankInfoService.findByAccountId(accountId);

		if (bankInfo.isPresent()) {
			final RequestEntity request = RequestEntity
				.get(new URI(accountUrl+"/"+accountId))
				.accept(MediaType.APPLICATION_JSON).build();

			AccountDTO accountDTO = oAuth2RestTemplate.exchange(request, AccountDTO.class).getBody();
			if (accountDTO != null) {
				BankDTO bankDTO = BankDTO.builder()
					.accountDTO(accountDTO)
					.iban(replaceIBAN(bankInfo.get().getIban(), accountId.toString()))
					.build();
				return new ResponseEntity<>(bankDTO, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	private String replaceIBAN(String iban, String accountId) {
		return iban.substring(0, (iban.length() - accountId.length())) + accountId;
	}
}
