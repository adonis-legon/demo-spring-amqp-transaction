package com.example.demospringamqptransaction;

import java.util.Arrays;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class RabbitClient {
    @Autowired
    RabbitConfig rabbitConfig;

    @Autowired
    RabbitAdmin rabbitAdmin;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @PostConstruct
    private void init(){
        DirectExchange directExchange = new DirectExchange(rabbitConfig.getRabbitExchange());
        rabbitAdmin.declareExchange(directExchange);

        Arrays.asList(rabbitConfig.getRabbitRoutingkeysAndQueues().split(",")).stream().forEach(routingKeyAndQueue -> {
            String routingKey = routingKeyAndQueue.split(":")[0];
            String queueName = routingKeyAndQueue.split(":")[1];

            Queue queue = new Queue(queueName);
            rabbitAdmin.declareQueue(queue);

            Binding binding = BindingBuilder.bind(queue).to(directExchange).with(routingKey);
            rabbitAdmin.declareBinding(binding);
        });
    }

    @Transactional
    public void sendMessageToAllQueues(Message message){
        Arrays.asList(rabbitConfig.getRabbitRoutingkeysAndQueues().split(",")).stream().forEach(routingKeyAndQueue -> {
            String routingKey = routingKeyAndQueue.split(":")[0];

            // Simulate possible error when writing to one of the queues
            if(new Random().nextInt(10) == 0){
                throw new MessageException();
            }

            rabbitTemplate.convertAndSend(rabbitConfig.getRabbitExchange(), routingKey, message);
        });
    }
}
