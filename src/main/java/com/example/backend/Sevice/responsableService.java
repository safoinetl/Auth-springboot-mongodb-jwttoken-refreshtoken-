package com.example.backend.Sevice;

import com.example.backend.Config.JwtService;
import com.example.backend.DTO.ActivityDto;
import com.example.backend.DTO.ChildDto;
import com.example.backend.DTO.GroupRequest;
import com.example.backend.DTO.UserDto;
import com.example.backend.Token.Token;
import com.example.backend.Token.TokenType;
import com.example.backend.auth.authentificationResponse;
import com.example.backend.repository.*;
import com.example.backend.schema.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class responsableService {
    @Autowired
    private ChildRepository childRepository;
    @Autowired
    private groupRepository groupRepository;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired

    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository repository;


    public child addingChild(ChildDto request) {
        child newChild = new child();
        newChild.setName(request.getName());
        newChild.setDisabledCard(request.getDisabledCard());
        newChild.setLastName(request.getLastName());
        newChild.setAge(request.getAge());
        newChild.setBirthDate(request.getBirthDate());
        return childRepository.save(newChild);
    }

    public List<child> listChild() {
        return this.childRepository.findAll();
    }

    public void deleteChild(String id) {
        this.childRepository.deleteById(id);
    }

    public String addChildToGrp(String childId, String groupId) {

        Optional<child> searchChild = childRepository.findById(childId);
        if (searchChild.isEmpty()) {
            throw new IllegalArgumentException(" child not found.");
        }
        Optional<group> searchGroup = this.groupRepository.findById(groupId);
        if (searchGroup.isEmpty()) {
            throw new IllegalArgumentException(" group not found.");
        }
        child newChild = searchChild.get();
        System.out.println(newChild);
        group groupFound = searchGroup.get();
        System.out.println(groupFound);
        if (groupFound.getChildren().contains(newChild)) {
            return "Child is already in the group.";
        }

        groupFound.getChildren().add(newChild);
        groupRepository.save(groupFound);
        newChild.setGroup(groupFound);
        childRepository.save(newChild);


        return "Child added successfully to the group.";
    }

    public group getGroupById(String id) {

        Optional<group> gp = this.groupRepository.findById(id);
        return gp.isPresent() ? gp.get() : null;

    }

    public List<activity> listActivity() {
        return this.activityRepository.findAll();
    }

    public void deleteActivity(String id) {
        this.activityRepository.deleteById(id);
    }

    public activity addActivity(ActivityDto request, String id) {
        group grp = groupRepository.findByUserG(id).get();
        activity newActivity = new activity();
        newActivity.setDescription(request.getDescription());
        newActivity.setStartingDate(request.getStartingDate());
        newActivity.setEndingDate(request.getEndingDate());
        newActivity.setUser(getCurrentUser());
        newActivity.setGroup(grp);
        grp.getActivities().add(newActivity);
        return activityRepository.save(newActivity);
    }

    public User addUser(UserDto request) {
        // Add user
        User newUser = new User();
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        newUser.setEmail(request.getEmail());
        newUser.setRole(Role.USER);
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        this.userRepository.save(newUser);
        // Add group
        var jwtToken = jwtService.generateToken(newUser);
        var refreshToken = jwtService.generateRefreshToken(newUser);

        saveUserToken(newUser, jwtToken);
        authentificationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
        return newUser;
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    public List<User> listUser() {
        return this.userRepository.findByRole("USER");
    }

    public void deleteUser(String id) {
        User user = this.userRepository.findById(id).get();
        if (user == null) {
            throw new IllegalStateException("User " + id + " is already deleted or does not exist");
        }
        this.userRepository.delete(user);
    }

    public note addNoteToChild(note note, String id) {
        Optional<child> child = this.childRepository.findById(id);
        child child1 = child.get();
        note newNote = new note();
        newNote.setSubject(note.getSubject());
        newNote.setDesc(note.getDesc());
        newNote.setChild(child1);
        noteRepository.save(newNote);
        child1.getNotes().add(newNote);
        childRepository.save(child1);
        return newNote;
    }

    public List<note> listNote() {
        return this.noteRepository.findAll();
    }

    public Optional<group> getUsersGroup() {
        Optional<User> CurrentUser = this.userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        return this.groupRepository.findByUserG(CurrentUser.get().getId());
    }

    public User getCurrentUser() {
        Optional<User> user = this.repository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        return user.get();
    }

    public List<group> listGroups() {
        return groupRepository.findAll();
    }

    public Optional<child> findChildById(String id) {
        return childRepository.findById(id);
    }

    public List<note> listChildNotes(String id, Date startDate, Date endDate) {
        Optional<child> child = childRepository.findById(id);
        Object childId = child.get().getId();
        return noteRepository.findNoteByUserAndTimeRange(childId, startDate, endDate);
    }

    public group addGroup(GroupRequest group, String userId) {
        User user = userRepository.findById(userId).get();
            group newGroup = new group();
            newGroup.setNameG(group.getNameG());
            newGroup.setDesc(group.getDesc());
            newGroup.setUserG(user);
            groupRepository.save(newGroup);
            user.getGroups().add(newGroup);
            userRepository.save(user);
            return newGroup;
    }

    public List<activity> UserlistActivity(String id) {

        return this.activityRepository.findByUser(id);
    }

    public Object EmpGroupList(String id) {
        return this.groupRepository.findByUserG(id);
    }
}

