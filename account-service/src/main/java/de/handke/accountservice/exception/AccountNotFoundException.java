package de.handke.accountservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such account")
public class AccountNotFoundException extends RuntimeException {
	public AccountNotFoundException(long id) {
		super("unable to find account with id ["+id+"]");
	}
}
