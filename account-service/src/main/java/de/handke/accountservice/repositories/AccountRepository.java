package de.handke.accountservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import de.handke.accountservice.entities.Account;


public interface AccountRepository extends JpaRepository<Account, Long> {

}
