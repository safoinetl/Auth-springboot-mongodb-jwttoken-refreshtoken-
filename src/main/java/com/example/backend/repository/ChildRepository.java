package com.example.backend.repository;
import com.example.backend.schema.child;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ChildRepository extends MongoRepository<child, String> {
}
