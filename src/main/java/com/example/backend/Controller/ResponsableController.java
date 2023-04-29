package com.example.backend.Controller;

import com.example.backend.DTO.ActivityDto;
import com.example.backend.DTO.ChildDto;
import com.example.backend.DTO.UserDto;
import com.example.backend.Sevice.responsableService;
import com.example.backend.schema.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/registerChild")
    public ResponseEntity<child> register(
            @RequestBody ChildDto request
    ) {
        return ResponseEntity.ok(service.addingChild(request));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/group/{userId}")
        public ResponseEntity<String> addChildToGroup(@RequestBody child childId , @PathVariable User userId,@RequestBody groupDTO request ) {
        try {
            System.out.println(childId);
            System.out.println(userId);
            String response = service.addChildToGrp(childId, userId,request);
            return ResponseEntity.ok(response);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/group/{id}")
    public ResponseEntity<group> search(@PathVariable() String id ){
        return ResponseEntity.ok(service.getGroupById(id));

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listChild")
    public List<child> listChild() {
        return this.service.listChild();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteChild/{id}")
    public void deleteChild(@PathVariable String id) {
        this.service.deleteChild(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/registerActivity")
    public ResponseEntity<activity> registerActivity(
            @RequestBody ActivityDto request
    ) {
        return ResponseEntity.ok(service.addActivity(request));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listActivity")
    public List<activity> listActivity() {
        return this.service.listActivity();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteActivity/{id}")
    public void deleteActivity(@PathVariable String id) {
        this.service.deleteActivity(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/registerUser")
    public ResponseEntity<User> registerActivity(
            @RequestBody UserDto request
    ) {
        return ResponseEntity.ok(service.addUser(request));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listUser")
    public List<User> listUser() {
        return this.service.listUser();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteActivity/{id}")
    public void deleteUser(@PathVariable String id) {
        this.service.deleteUser(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listNote")
    public void listNote() {
        this.service.listNote();
    }
}
