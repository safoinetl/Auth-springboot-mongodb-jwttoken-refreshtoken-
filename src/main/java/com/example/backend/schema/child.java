package com.example.backend.schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;


import java.util.Date;
@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class child {
    @MongoId(FieldType.OBJECT_ID)
    private String id;
    private String name;
    private String LastName;
    private Date BirthDate;
    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private Date lastModifiedAt;
    @DBRef
    private group group;
}
