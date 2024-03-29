package de.handke.bankinfoservice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
	private Long id;
	private String firstName;
	private String lastName;
}
