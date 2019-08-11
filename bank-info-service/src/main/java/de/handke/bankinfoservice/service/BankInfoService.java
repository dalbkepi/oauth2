package de.handke.bankinfoservice.service;

import java.net.URISyntaxException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.handke.bankinfoservice.entities.BankInfo;
import de.handke.bankinfoservice.repository.BankInfoRepository;


@Service
public class BankInfoService {

	@Autowired
	private BankInfoRepository bankInfoRepository;


	public Optional<BankInfo> findByAccountId(Long accountId) throws URISyntaxException {

		return bankInfoRepository.findByAccountId(accountId);

	}

}
