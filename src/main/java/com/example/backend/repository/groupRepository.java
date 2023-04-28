package com.example.backend.repository;

import com.example.backend.schema.group;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface groupRepository extends MongoRepository<group, String> {
}
