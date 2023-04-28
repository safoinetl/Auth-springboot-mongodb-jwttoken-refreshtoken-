package com.example.backend.schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class groupDTO {
    @Indexed
    private String nameG;
}
