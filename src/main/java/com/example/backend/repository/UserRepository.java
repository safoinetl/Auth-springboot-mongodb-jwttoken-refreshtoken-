package com.example.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.backend.schema.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    //Optional<Object> findByEmail(String userEmail);
     Optional<User> findByEmail(String email);

}
