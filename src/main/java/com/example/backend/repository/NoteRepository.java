package com.example.backend.repository;

import com.example.backend.schema.activity;
import com.example.backend.schema.note;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NoteRepository extends MongoRepository<note, String>  {

}
