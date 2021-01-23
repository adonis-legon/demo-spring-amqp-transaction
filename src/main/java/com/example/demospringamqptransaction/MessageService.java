package com.example.demospringamqptransaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    @Autowired
    RabbitClient rabbitClient;

    public void sendMessage(Message message){
        rabbitClient.sendMessageToAllQueues(message); 
    }
}
