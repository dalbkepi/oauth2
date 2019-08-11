package de.handke.bankinfoservice.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BankInfo {

	@Id
	private Long id;
	private Long accountId;
	private String iban;
}
