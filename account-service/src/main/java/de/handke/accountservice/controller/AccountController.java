package de.handke.accountservice.controller;

import static de.handke.accountservice.controller.AccountControllerUtils.*;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.handke.accountservice.controller.dto.AccountDTO;
import de.handke.accountservice.service.AccountService;


@RestController
public class AccountController {

	@Autowired
	private AccountService accountService;


	@GetMapping(value = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AccountDTO>> getAccounts() {
		return new ResponseEntity<>(mapAccountsToDtos(accountService.findAll()), HttpStatus.OK);
	}

	@GetMapping(value = "/account/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AccountDTO> getAccount(@PathVariable(value = "accountId") Long accountId) {
		return new ResponseEntity<>(mapAccountToDto(accountService.findOne(accountId)) , HttpStatus.OK);
	}

	@PostMapping(value = "/account/{accountId}")
	public ResponseEntity createAccount(@PathVariable(value = "accountId") Long accountId, @RequestBody AccountDTO accountDTO) {
		accountDTO.setId(accountId);
		accountService.save(mapAccountDtoToAccount(accountDTO));
		return new ResponseEntity(HttpStatus.OK); // 201 fits better here
	}



	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public void handleMethodArgumentNotValid(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setAttribute(DefaultErrorAttributes.class.getName() + ".ERROR", null);
		response.sendError(HttpStatus.BAD_REQUEST.value(), "Validation error");
	}
}
