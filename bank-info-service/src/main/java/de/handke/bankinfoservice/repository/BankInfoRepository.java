package de.handke.bankinfoservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import de.handke.bankinfoservice.entities.BankInfo;


public interface BankInfoRepository extends JpaRepository<BankInfo, Long> {
	Optional<BankInfo> findByAccountId(long accountId);
}
