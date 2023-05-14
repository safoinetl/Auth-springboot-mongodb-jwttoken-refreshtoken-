package com.example.backend.Controller;

import com.example.backend.DTO.*;
import com.example.backend.Sevice.responsableService;
import com.example.backend.schema.*;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@SecurityScheme(name = "BearerAuth",
        scheme = "Bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER)
@RestController
@RequestMapping("/api/responsable")
@RequiredArgsConstructor
@SecurityRequirement(name = "BearerAuth")
public class ResponsableController {
    private responsableService service;

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
    @PostMapping("/group/user/{groupId}/child/{childId}")
    public ResponseEntity<String> addChildToGroup(@PathVariable String childId, @PathVariable String groupId) {
        String response = service.addChildToGrp(childId, groupId);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/group/{id}")
    public ResponseEntity<group> search(@PathVariable() String id) {
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
            @RequestBody ActivityDto request, @PathVariable String id
    ) {
        return ResponseEntity.ok(service.addActivity(request, id));
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

    @PreAuthorize("hasRole('ADMIN')")
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

    @GetMapping("/usersGroup")
    public Optional<group> findGroupByUser() {
        return this.service.getUsersGroup();
    }

    @GetMapping("/CurrentAuthent")
    public ResponseEntity<Object> user(
    ) {
        return
                ResponseEntity.ok()
                        .body(service.getCurrentUser());
    }

    @GetMapping("/GroupList")
    public List<group> listOfGroups() {
        return service.listGroups();
    }

    @GetMapping("/child/{id}")
    public Optional<child> findChild(@PathVariable String id) {
        return this.service.findChildById(id);
    }

    @PostMapping("/AddchildNote/{id}")
    public note childNote(@RequestBody NoteDto note, @PathVariable String id) {
        return this.service.addNoteToChild(note, id);
    }
    @GetMapping("/{childId}/notes")
    public List<note> getChildNotesWithinTimeInterval(
            @PathVariable String childId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endDate) {
        return service.listChildNotes(childId, startDate, endDate);
    }
    @PostMapping("/groups/{userId}")
    public ResponseEntity<group> addGroup(@RequestBody GroupRequest groupRequest, @PathVariable String userId) {
        group group = service.addGroup(groupRequest, userId);
        return ResponseEntity.ok(group);
    }

    @GetMapping("/{userId}/activities")
    public List<activity> getUserActivities(@PathVariable String userId) {
        return service.UserlistActivity(userId);
    }
    @GetMapping("/{userId}/groups")
    public Object EmpGroup(@PathVariable String userId ){
        return service.EmpGroupList(userId);
    }
}
