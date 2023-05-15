package com.example.backend.repository;

import com.example.backend.schema.group;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface groupRepository extends MongoRepository<group, String> {

    group findByUserG(String userG);
}
