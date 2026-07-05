package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

  private final AccountRepository accountRepository;

  @Autowired
  public AccountService(AccountRepository accountRepository){
    this.accountRepository = accountRepository;
  }

  public Account register(Account account){
     /*
        Registration requires a non-blank username and a password longer than 4 characters 
     */

     if (!isValidRegistration(account)) {
      return null;
     }

     if (usernameExists(account.getUsername())) {
      return null;
     }

      return accountRepository.save(account);
  }

  public boolean isValidRegistration(Account account){

      return account != null &&
             account.getUsername() != null &&
             !account.getUsername().isBlank() &&
             account.getPassword() != null &&
             account.getPassword().length() > 4;
  }

  public boolean usernameExists(String username) {

      return username != null &&
              accountRepository.existsByUsername(username);
  }

  public Account login(Account account){

    /*
        login succeeds only when the stored password matches the request password .
    */

      if (account == null ||
          account.getUsername() == null ||
          account.getPassword() == null
         ) {
                return null;
      }

      Optional<Account> foundAccount =
                            accountRepository.findByUsername(account.getUsername());

          if (foundAccount.isPresent()
                      &&
              foundAccount.get().getPassword().equals(account.getPassword()) ) {
                  return foundAccount.get();
          }
          
          return null;

  }

  public boolean existsById(Integer accountId){
      return accountId != null &&
             accountRepository.existsById(accountId);
  }

}
