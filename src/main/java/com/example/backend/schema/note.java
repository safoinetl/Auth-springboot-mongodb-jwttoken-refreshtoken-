package com.example.backend.schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

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
    @CreationTimestamp
    private LocalTime createdAt= LocalTime.now(ZoneId.of("GMT+08:00"));
    @UpdateTimestamp
    private LocalTime updatedAt = LocalTime.now(ZoneId.of("GMT+08:00"));
    @DBRef
    private User user;
}
