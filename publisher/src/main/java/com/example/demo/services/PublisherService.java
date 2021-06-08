package com.example.demo.services;

import com.example.demo.config.RabbitMQConfiguration;
import com.example.demo.dto.Cars;
import com.example.demo.repository.CarsRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@EnableScheduling
public class PublisherService {

    private final RabbitTemplate rabbitTemplate;
    private final CarsRepository repository;

    public PublisherService(RabbitTemplate rabbitTemplate, CarsRepository repository) {
        this.rabbitTemplate = rabbitTemplate;
        this.repository = repository;
    }

    @Scheduled(fixedRate = 10 * 1000)
    public void send(){
        rabbitTemplate.convertAndSend(RabbitMQConfiguration.TOPIC_EXCHANGE_NAME, "cars.new", getCars());
    }

    private List<Cars> getCars() {
        List<Cars> cars = new ArrayList<>();
        cars.add(Cars.builder().amount(new BigDecimal(2500)).name("Camry").isSold(false).build());
        cars.add(Cars.builder().amount(new BigDecimal(5000)).name("GX470").isSold(false).build());
        cars.add(Cars.builder().amount(new BigDecimal(2500)).name("Avensis").isSold(false).build());
        cars.add(Cars.builder().amount(new BigDecimal(25000)).name("Corolla").isSold(false).build());
        cars.add(Cars.builder().amount(new BigDecimal(25000)).name("Murano").isSold(false).build());
        cars.add(Cars.builder().amount(new BigDecimal(25000)).name("Tucano").isSold(false).build());
        cars.forEach(System.out::println);
        cars = repository.saveAll(cars);
        return cars;
    }

    @RabbitListener(queues = "consumer-queue", containerFactory = "listenerContainerFactory")
    public void receiveMessage(List<Cars> cars) throws Exception{
        cars = repository.saveAll(cars);
        cars.forEach(System.out::println);
    }
}
