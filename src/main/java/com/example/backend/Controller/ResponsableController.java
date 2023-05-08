package com.example.backend.Controller;

import com.example.backend.DTO.ActivityDto;
import com.example.backend.DTO.ChildDto;
import com.example.backend.DTO.UserDto;
import com.example.backend.Sevice.responsableService;
import com.example.backend.schema.*;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:8080/api/responsable", maxAge = 3600)
@RequestMapping("/api/responsable")
@RequiredArgsConstructor
public class ResponsableController {
    private  responsableService service;

    @Autowired
    public ResponsableController(responsableService service) {
        this.service = service;
    }

    //@PreAuthorize("hasRole('USER')")
    @PostMapping("/registerChild")
    public ResponseEntity<child> register(
            @RequestBody ChildDto request
    ) {
        return ResponseEntity.ok(service.addingChild(request));
    }
    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/group/user/{userId}/child/{childId}")
        public ResponseEntity<String> addChildToGroup(@PathVariable ObjectId childId    , @PathVariable String userId ) {
        try {
            String response = service.addChildToGrp(childId, userId);
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
    @PostMapping("/registerActivity/{id}")
    public ResponseEntity<activity> registerActivity(
            @RequestBody ActivityDto request,@PathVariable String id
    ) {
        return ResponseEntity.ok(service.addActivity(request,id));
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

    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/registerUser")
    public ResponseEntity<User> registerActivity(
            @RequestBody UserDto request
    ) {
        return ResponseEntity.ok(service.addUser(request));
    }
    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listUser")
    public List<User> listUser() {

        return
               this.service.listUser();

    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteUser/{id}")
    public void deleteUser(@PathVariable String id) {
        this.service.deleteUser(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listNote")
    public void listNote() {
        this.service.listNote();
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/usersGroup")
    public Optional<group> findGroupByUser()
            {
                return this.service.getUsersGroup();
            }
}
