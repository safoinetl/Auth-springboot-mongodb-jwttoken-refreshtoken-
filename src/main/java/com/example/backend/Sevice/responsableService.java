package com.example.backend.Sevice;

import com.example.backend.Config.JwtService;
import com.example.backend.DTO.ActivityDto;
import com.example.backend.DTO.ChildDto;
import com.example.backend.DTO.UserDto;
import com.example.backend.Token.Token;
import com.example.backend.Token.TokenType;
import com.example.backend.auth.authentificationResponse;
import com.example.backend.repository.*;
import com.example.backend.schema.*;
import org.bson.types.ObjectId;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class responsableService {
    private final ChildRepository childRepository;
    private final groupRepository groupRepository;
    private final ActivityRepository activityRepository;
    private final NoteRepository noteRepository;
    private final TokenRepository tokenRepository;

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public responsableService(ChildRepository childRepository, UserRepository repository, groupRepository groupRepository, ActivityRepository activityRepository, NoteRepository noteRepository, TokenRepository tokenRepository, JwtService jwtService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.childRepository = childRepository;
        this.groupRepository = groupRepository;
        this.activityRepository = activityRepository;
        this.noteRepository = noteRepository;
        this.tokenRepository = tokenRepository;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

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

    public String addChildToGrp(ObjectId childId, String userG) {

        Optional<child> searchChild = childRepository.findById(childId.toString());
        if (searchChild.isEmpty()) {
            throw new IllegalArgumentException(" child not found.");
        }
        Optional<group> searchGroup = groupRepository.findByUserG(userG);
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
        var grp = getGroupById(id);
        activity newActivity = new activity();
        newActivity.setDescription(request.getDescription());
        newActivity.setStartingDate(request.getStartingDate());
        newActivity.setEndingDate(request.getEndingDate());
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
        group grp = new group();
        grp.setNameG(newUser.getFirstName() + " " + "group");
        grp.setUserG(newUser);
        groupRepository.save(grp);
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
        return this.userRepository.findAll();
    }

    public void deleteUser(String id) {
        this.activityRepository.deleteById(id);
    }

    public List<note> listNote() {
        return this.noteRepository.findAll();
    }
    public Optional<group> getUsersGroup() {
         Optional<User> CurrentUser = this.userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        return this.groupRepository.findByUserG(CurrentUser.get().getId());
    }
}

