package com.example.backend.schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class groupDTO {
    @Indexed
    private String nameG;
    @Indexed
    @DBRef
    private List<child> children;
}
