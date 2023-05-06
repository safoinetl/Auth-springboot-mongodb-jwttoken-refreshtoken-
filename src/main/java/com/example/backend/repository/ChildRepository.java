package com.example.backend.repository;
import com.example.backend.schema.child;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;


public interface ChildRepository extends MongoRepository<child, String> {
}
