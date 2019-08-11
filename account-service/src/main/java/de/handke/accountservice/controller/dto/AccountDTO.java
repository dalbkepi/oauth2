package de.handke.accountservice.controller.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class AccountDTO {
	private long id;
	private String firstName;
	private String lastName;
}

