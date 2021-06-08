package com.example.demo.services;

import com.example.demo.config.RabbitMQConfiguration;
import com.example.demo.dto.Cars;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class AsyncMethod {

    private final RabbitTemplate rabbitTemplate;

    public AsyncMethod(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Async
    public CompletableFuture<List<Cars>> send(List<Cars> cars) throws InterruptedException {
        for (Cars car: cars
             ) {
            car.setSold(true);
        }
        Thread.sleep(1000L);
        rabbitTemplate.convertAndSend(RabbitMQConfiguration.TOPIC_EXCHANGE_NAME, "cars.updated", cars);
        return CompletableFuture.completedFuture(cars);
    }
}
