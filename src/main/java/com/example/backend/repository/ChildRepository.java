package com.example.backend.repository;

import com.example.backend.schema.child;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface ChildRepository extends MongoRepository<child, String> {
    //Optional<child> getchildBy(String id);
}
