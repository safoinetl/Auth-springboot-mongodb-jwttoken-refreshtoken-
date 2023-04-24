package com.example.backend.Controller;

import com.example.backend.DTO.ChildDto;
import com.example.backend.Sevice.responsableService;
import com.example.backend.auth.authentificationResponse;
import com.example.backend.auth.registerRequest;
import com.example.backend.schema.child;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/responsable")
@RequiredArgsConstructor
@CrossOrigin
public class ResponsableController {
    @Autowired
    private  responsableService service;
    @PostMapping("/registerChild")
    public ResponseEntity<child> register(
            @RequestBody ChildDto request
    ) {
        return ResponseEntity.ok(service.addingChild(request));
    }

}
