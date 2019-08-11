package de.handke.accountservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.handke.accountservice.entities.Account;
import de.handke.accountservice.exception.AccountAlreadyExistException;
import de.handke.accountservice.exception.AccountNotFoundException;
import de.handke.accountservice.repositories.AccountRepository;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;


	public Account save(Account account) {
		log.debug("save account {}", account);
		try {
			Account storedAccount = findOne(account.getId());
			if (storedAccount != null) {
				log.error("account {} already existing", account);
				throw new AccountAlreadyExistException(account.getId());
			}
		} catch (AccountNotFoundException e) {
			// do nothing
		}

		return accountRepository.save(account);
	}

	public List<Account> findAll() {
		log.debug("find all accounts");
		return accountRepository.findAll();
	}

	public Account findOne(Long id) {
		log.debug("find account with id {}", id);
		Optional<Account> account = accountRepository.findById(id);
		if (account.isPresent()) {
			return account.get();
		}
		throw new AccountNotFoundException(id);
	}
}
