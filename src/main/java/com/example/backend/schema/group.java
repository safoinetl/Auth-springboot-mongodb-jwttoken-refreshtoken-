package com.example.backend.schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;
import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class group {
    @MongoId(FieldType.OBJECT_ID)
    private String id;
    @LastModifiedDate
    private Date lastModifiedAt;
    @DBRef
    private List<child> children;
    @DBRef
    private User user;
}
