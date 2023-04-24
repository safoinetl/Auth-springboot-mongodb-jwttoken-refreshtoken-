package com.example.backend.DTO;

import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ChildDto {
    private String name;
    private String lastName;
    private Integer age;
    private Date BirthDate;
}
