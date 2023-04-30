package com.example.backend.schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class activity {
    @MongoId(FieldType.OBJECT_ID)
    private String id;
    private String description;
    private Date startingDate;
    private Date endingDate;
    @CreationTimestamp
    private LocalTime createdAt= LocalTime.now(ZoneId.of("GMT+08:00"));
    @UpdateTimestamp
    private LocalTime updatedAt = LocalTime.now(ZoneId.of("GMT+08:00"));
    @DBRef
    private List<group> groups;

    public List<group> getGroup() {
        if (groups == null) {
            groups = new ArrayList<>();
        }
        else {
            groups = groups;
        }
        return groups;
    }


}
