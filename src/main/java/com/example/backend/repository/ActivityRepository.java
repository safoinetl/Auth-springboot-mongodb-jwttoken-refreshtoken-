package com.example.backend.repository;

import com.example.backend.schema.activity;
import com.example.backend.schema.child;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ActivityRepository extends MongoRepository<activity, String> {
    @Query( "{'id': ?0}")
    List<activity> findByUser(String id);
}
