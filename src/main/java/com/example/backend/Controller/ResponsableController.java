package com.example.backend.Controller;

import com.example.backend.DTO.ChildDto;
import com.example.backend.Sevice.responsableService;
import com.example.backend.schema.User;
import com.example.backend.schema.child;
import com.example.backend.schema.group;
import com.example.backend.schema.groupDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/responsable")
@RequiredArgsConstructor
@CrossOrigin
public class ResponsableController {
    private  responsableService service;

    @Autowired
    public ResponsableController(responsableService service) {
        this.service = service;
    }

    @PostMapping("/registerChild")
    public ResponseEntity<child> register(
            @RequestBody ChildDto request
    ) {
        return ResponseEntity.ok(service.addingChild(request));
    }

    @PostMapping("/group/{userId}")
        public ResponseEntity<String> addChildToGroup(@RequestBody child childId , @PathVariable User userId,@RequestBody ) {
        try {groupDTO request
            System.out.println(childId);
            System.out.println(userId);
            String response = service.addChildToGrp(childId, userId,request);
            return ResponseEntity.ok(response);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
