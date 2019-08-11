package de.handke.accountservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Account already existing")
public class AccountAlreadyExistException extends RuntimeException{
	public AccountAlreadyExistException(long id) {
		super("unable to create account with id ["+id+"]");
	}

}
