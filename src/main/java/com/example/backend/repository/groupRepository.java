package com.example.backend.repository;

import com.example.backend.schema.group;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface groupRepository extends MongoRepository<group, String> {
    @Query( "{'userG': ?0}")
    Optional<group> findByUserG(String userG);
}
