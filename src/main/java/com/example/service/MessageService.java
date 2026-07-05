package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

  private final MessageRepository messageRepository;
  private final AccountService accountService;
 
  @Autowired
  public MessageService(MessageRepository messageRepository,
                        AccountService accountService){
        this.messageRepository = messageRepository;
        this.accountService = accountService;  
  }

  public Message createMessage(Message message){
    // New message require valid text and an existing posting account

      if (!isValidMessageText(message)) {
        return null;

      }

      if (!accountService.existsById(message.getPostedBy())) {
        return null;
      }
      return messageRepository.save(message);
  }

  public List<Message> getAllMessages(){
   
      // Tests expect messages orderd by messageId ascending
    return 
      messageRepository.findAllByOrderByMessageIdAsc();

  }

  public Message getMessageById(Integer messageId){
      
    Optional<Message> message = messageRepository.findById(messageId);
    
    return message.orElse(null);

  }

  public Integer deleteMessageById(Integer messageId){

    if (messageId == null || !messageRepository.existsById((messageId))) {
      return null;
    }

    messageRepository.deleteById(messageId);
    return 1;

  }

  public Integer updateMessageText(Integer messageId, Message message){

      // Patch updates only messageText after validation
     if (messageId == null || !isValidMessageText(message)) {
      return null;
     } 

     Optional<Message> foundMessage = messageRepository.findById(messageId);

     if (foundMessage.isEmpty()) {
      return null;
     }

     Message existingMessage = foundMessage.get();
     existingMessage.setMessageText(message.getMessageText());
     messageRepository.save(existingMessage);

     return 1;
  }

  public List<Message> getMessagesByAccountId(Integer accountId){

    return
      messageRepository.findByPostedByOrderByMessageIdAsc(accountId);
  }

  private boolean isValidMessageText(Message message) {

    return message != null
            && message.getMessageText() != null
            && !message.getMessageText().isBlank()
            && message.getMessageText().length() <= 255;
  }

}
