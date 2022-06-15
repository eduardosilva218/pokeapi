package com.eduardosilva218.pokeapi;

import com.eduardosilva218.pokeapi.repositories.interfaces.IItemRepository;
import com.eduardosilva218.pokeapi.repositories.interfaces.ITrainerRepository;
import io.mongock.runner.springboot.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongock
@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = {IItemRepository.class, ITrainerRepository.class})
public class PokeApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(PokeApiApplication.class, args);
    }
}
