package com.example.backend.Controller;

import com.example.backend.Sevice.authentificationService;
import com.example.backend.auth.authentificationRequest;
import com.example.backend.auth.authentificationResponse;
import com.example.backend.auth.registerRequest;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class authentificationController {
    private final authentificationService service;

    @PostMapping("/register")
    public ResponseEntity<authentificationResponse> register(
            @RequestBody registerRequest request
    ) {
        return ResponseEntity.ok(service.registerAdmin(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<authentificationResponse> authenticate(
            @RequestBody authentificationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));

    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken( request, response);
    }

}
