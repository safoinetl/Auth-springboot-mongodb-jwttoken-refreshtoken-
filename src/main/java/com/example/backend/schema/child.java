package com.example.backend.schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;


import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Document
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class child {
    @MongoId(FieldType.OBJECT_ID)
    private String id;
    private String name;
    private String lastName;
    private Integer age;
    private Date BirthDate;
    @Indexed
    private String disabledCard;
    private LocalTime createdAt= LocalTime.now(ZoneId.of("GMT+08:00"));

    private LocalTime updatedAt = LocalTime.now(ZoneId.of("GMT+08:00"));
    @DBRef
    private group group;
    @DBRef
    private List<note> notes;
    public List<note> getActivities() {
        if (notes == null) {
            return new ArrayList<>();
        }
        return notes;
    }
}
