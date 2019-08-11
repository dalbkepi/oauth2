package de.handke.bankinfoservice.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class BankDTO {

	@JsonUnwrapped
	private AccountDTO accountDTO;

	@JsonProperty("IBAN")
	private String iban;
}
