package com.example.backend.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class registerRequest {
    private String firstname;
    private String lastname;
    @Indexed(unique= true)
    private String email;
    private String password;
}
