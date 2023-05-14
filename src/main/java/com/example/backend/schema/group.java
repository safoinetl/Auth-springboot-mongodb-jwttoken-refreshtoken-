package com.example.backend.schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class group {
    @MongoId(FieldType.OBJECT_ID)
    private String id;
    @Indexed
    private String nameG;
    @CreatedDate
    private LocalTime createdAt = LocalTime.now(ZoneId.of("GMT+08:00"));
    @LastModifiedDate
    private LocalTime updatedAt = LocalTime.now(ZoneId.of("GMT+08:00"));
    @DBRef
    private List<child> children;
    @DBRef
    private User userG;
    @DBRef
    private List<activity> activities;
    private String desc;

    public List<child> getChildren() {
        if (children == null) {
            children = new ArrayList<>();
        }
        return children;
    }
    public List<activity> getActivities() {
        if (activities == null) {
         return new ArrayList<>();
        }
        return activities;
    }


}
