package com.example.backend.repository;

import com.example.backend.schema.activity;
import com.example.backend.schema.child;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActivityRepository extends MongoRepository<activity, String> {
}
