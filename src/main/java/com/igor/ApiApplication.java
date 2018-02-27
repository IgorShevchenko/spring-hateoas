package com.igor;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.igor.model.Member;
import com.igor.repository.MemberRestRepository;

@SpringBootApplication
public class ApiApplication {

    // TODO:
    // Logging of requests
    // Zokeeper
    // Documentation
    // Error code, data validation
    // Add links to methods

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

    @Bean
    CommandLineRunner registerAtZookeeper() {
        return (args) -> {
            LOGGER.info("Registering at Zookeeper...");
        };
    }

    @Bean
    CommandLineRunner demo(MemberRestRepository repository) {
        return (args) -> {
            LOGGER.info("Creating a couple of demo members...");

            Member igor = new Member();
            igor.setFirstName("Ben");
            igor.setLastName("Musterman");
            igor.setZipcode("10141");
            igor.setDateOfBirth(LocalDate.of(1990, 10, 15));
            igor = repository.save(igor);

            Member max = new Member();
            max.setFirstName("Max");
            max.setLastName("Musterman");
            max.setZipcode("10250");
            max.setDateOfBirth(LocalDate.of(1980, 5, 7));
            max = repository.save(max);
        };
    }

}
