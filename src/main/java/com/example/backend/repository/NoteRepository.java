package com.example.backend.repository;

import com.example.backend.schema.activity;
import com.example.backend.schema.note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

public interface NoteRepository extends MongoRepository<note, String>  {

    @Query(value = "{ 'user': ?0, 'timestamp': { $gte: ?1, $lte: ?2 } }")
    List<note> findNoteByUserAndTimeRange(Object userId, Date startDate, Date endDate);
}
