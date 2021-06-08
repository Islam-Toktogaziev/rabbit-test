package com.example.demo.services;

import com.example.demo.config.RabbitMQConfiguration;
import com.example.demo.dto.Cars;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerService.class);

    private final AsyncMethod asyncMethod;

    public ConsumerService(AsyncMethod asyncMethod) {
        this.asyncMethod = asyncMethod;
    }

    @RabbitListener(queues = "publisher-queue", containerFactory = "listenerContainerFactory")
    public void receiveMessage(List<Cars> cars) throws Exception{
        List<Cars> cars1 = cars.subList(0, cars.size() / 2);
        List<Cars> cars2 = cars.subList(cars.size() / 2, cars.size());
        CompletableFuture<List<Cars>> thread1 = asyncMethod.send(cars1);
        CompletableFuture<List<Cars>> thread2 = asyncMethod.send(cars2);
        logger.info("thread1--> " + thread1.get());
        logger.info("thread2--> " + thread2.get());
    }


}
