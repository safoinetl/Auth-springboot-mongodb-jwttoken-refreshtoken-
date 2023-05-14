package com.example.backend.schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalTime;
import java.time.ZoneId;

@Document
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class note {
    @MongoId(FieldType.OBJECT_ID)
    private String id;
    private String subject;
    private String desc;

    private LocalTime createdAt= LocalTime.now(ZoneId.of("GMT+08:00"));
    private LocalTime updatedAt = LocalTime.now(ZoneId.of("GMT+08:00"));
    @DBRef
    private child child;
}
