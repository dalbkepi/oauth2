package de.handke.accountservice.controller;

import java.util.List;
import java.util.stream.Collectors;

import de.handke.accountservice.controller.dto.AccountDTO;
import de.handke.accountservice.entities.Account;


public class AccountControllerUtils {

	public static AccountDTO mapAccountToDto(Account account) {
		return AccountDTO.builder()
			.id(account.getId())
			.firstName(account.getFirstName())
			.lastName(account.getLastName())
			.build();
	}

	public static List<AccountDTO> mapAccountsToDtos(List<Account> accounts) {
		return accounts.stream().map(AccountControllerUtils::mapAccountToDto).collect(Collectors.toList());
	}

	public static Account mapAccountDtoToAccount(AccountDTO accountDTO) {
		return Account.builder()
			.id(accountDTO.getId())
			.firstName(accountDTO.getFirstName())
			.lastName(accountDTO.getLastName())
			.build();
	}


}
