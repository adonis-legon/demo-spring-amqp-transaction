package com.example.demospringamqptransaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MessageController {
    @Autowired
    MessageService messageService;

    @PostMapping(value = "/message")
    public ResponseEntity<String> createMessage(@RequestBody Message message){
        messageService.sendMessage(message);
        String okMessage = "Mensaje creado.";

        System.out.println(okMessage);
        return new ResponseEntity<>(okMessage, HttpStatus.ACCEPTED);
    }
}
