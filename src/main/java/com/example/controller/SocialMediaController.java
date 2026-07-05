package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController
public class SocialMediaController {

  private final AccountService accountService;
  private final MessageService messageService;

  @Autowired
  public SocialMediaController(AccountService accountService,
                              MessageService messageService){
    this.accountService = accountService;
    this.messageService = messageService;

    }

  @PostMapping("/register")
  public ResponseEntity<Account> register(@RequestBody Account account){

    if(!accountService.isValidRegistration(account)) {
      return ResponseEntity.status(400).build();
    }

    if (accountService.usernameExists(account.getUsername())) {
      return ResponseEntity.status(409).build();
    }

    Account registeredAccount = accountService.register(account);

      return ResponseEntity.status(200).body(registeredAccount);
  }

  @PostMapping("/login")
  public ResponseEntity<Account> login (@RequestBody Account account){

    Account loggedInAccount = accountService.login(account);

    if (loggedInAccount == null) {
      return ResponseEntity.status(401).build();
    }

    return ResponseEntity.status(200).body(loggedInAccount);
  }

  @PostMapping("/messages")
  public ResponseEntity<Message> createMessage(@RequestBody Message message){

    Message createdMessage = messageService.createMessage(message);

    if (createdMessage == null) {
      return ResponseEntity.status(400).build();
    }

    return ResponseEntity.status(200).body(createdMessage);
  }

  @GetMapping("/messages")
  public ResponseEntity<List<Message>> getAllMessages(){

      // return every message in deterministic order

    return ResponseEntity.status(200).body(messageService.getAllMessages());
  }

  @GetMapping("/messages/{messageId}")
  public ResponseEntity<Message> getMessageById(@PathVariable Integer messageId){

    Message message = messageService.getMessageById(messageId);

    if (message == null) {
      return ResponseEntity.status(200).build();
    }

    return ResponseEntity.status(200).body(message);
  }

  @DeleteMapping("/messages/{messageId}")
  public ResponseEntity<Integer> deleteMessageById(@PathVariable Integer messageId){

    Integer deletedRows = messageService.deleteMessageById(messageId);

    if (deletedRows == null) {
      return ResponseEntity.status(200).build();
    }

    return ResponseEntity.status(200).body(deletedRows);
  }

  @PatchMapping("/messages/{messageId}")
  public ResponseEntity<Integer> updateMessageText(
    @PathVariable Integer messageId,
    @RequestBody Message message
  ){
      Integer updatedRows = messageService.updateMessageText(messageId, message);

      if (updatedRows == null) {
        return ResponseEntity.status(400).build();
      }

      return ResponseEntity.status(200).body(updatedRows);
  }

  @GetMapping("/accounts/{accountId}/messages")
  public ResponseEntity<List<Message>> getMessagesByAccountId(@PathVariable Integer accountId){

    return ResponseEntity.status(200).body(messageService.getMessagesByAccountId(accountId));
  }

}
