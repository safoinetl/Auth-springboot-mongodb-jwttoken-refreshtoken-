package com.example.backend.Sevice;

import com.example.backend.DTO.ActivityDto;
import com.example.backend.DTO.ChildDto;
import com.example.backend.DTO.UserDto;
import com.example.backend.repository.*;
import com.example.backend.schema.*;
import org.bson.types.ObjectId;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class responsableService {
    private final ChildRepository childRepository;
    private final groupRepository groupRepository;
    private final ActivityRepository activityRepository;
    private final NoteRepository noteRepository;


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public responsableService(ChildRepository childRepository, UserRepository repository, groupRepository groupRepository, ActivityRepository activityRepository, NoteRepository noteRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.childRepository = childRepository;
        this.groupRepository = groupRepository;
        this.activityRepository = activityRepository;
        this.noteRepository = noteRepository;
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
        return  this.childRepository.findAll();
    }

    public void  deleteChild (String id) {
        this.childRepository.deleteById(id);
    }
    /*public String addChildToGrp(child childId, User userId, groupDTO request) {
        Optional<child> searchChild = this.childRepository.findById(childId.getId());
        Optional<User> searchUser = this.repository.findById(userId.getId());
        if (searchUser.isEmpty() || searchChild.isEmpty()) {
            throw new UsernameNotFoundException("Something went wrong. Please check your inputs.");
        }
        else {
            child newChild = searchChild.get();
            User newUser = searchUser.get();
            group newGroup = new group();
            System.out.println(request);
            newGroup.setUserG(newUser);
            newGroup.getChildren().add(newChild);
            newGroup.setNameG(request.getNameG());
            this.groupRepository.save(newGroup);
        }
        return "Group added successfully";
    }*/
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

        return "Child added successfully to the group.";
    }
    public String addChildsToGrp(List<String> childIds, String userG) {
        List<child> children = childRepository.findAllById(childIds);
        if (children.isEmpty()) {
            throw new IllegalArgumentException("No children found with the given IDs.");
        }
        Optional<group> searchGroup = groupRepository.findByUserG(userG);
        if (searchGroup.isEmpty()) {
            throw new IllegalArgumentException("Group not found.");
        }
        group groupFound = searchGroup.get();
        Set<child> groupChildren = (Set<child>) groupFound.getChildren();
        int initialGroupSize = groupChildren.size();
        children.forEach(child -> {
            if (!groupChildren.contains(child)) {
                groupChildren.add(child);
            }
        });
        if (groupChildren.size() == initialGroupSize) {
            return "All children are already in the group.";
        }
        groupRepository.save(groupFound);
        return "Children added successfully to the group.";
    }
    public group getGroupById(String id) {

    Optional<group> gp = this.groupRepository.findById(id);
    return gp.isPresent() ? gp.get(): null;

    }

    public List<activity> listActivity() {
        return  this.activityRepository.findAll();
    }
    public void deleteActivity(String id) {
        this.activityRepository.deleteById(id);
    }
    public activity addActivity(ActivityDto request) {
        activity newActivity = new activity();
        newActivity.setDescription(request.getDescription());
        newActivity.setStartingDate(request.getStartingDate());
        newActivity.setEndingDate(request.getEndingDate());
        return activityRepository.save(newActivity);
    }
    public User addUser(UserDto request) {
        // Add user
        User newUser= new User();
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        newUser.setEmail(request.getEmail());
        newUser.setRole(Role.USER);
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        this.userRepository.save(newUser);

        // Add group
        group grp = new group();
        grp.setNameG(newUser.getUsername()+"group");
        grp.setUserG(newUser);
        groupRepository.save(grp);

        return newUser;
    }

    public List<User> listUser() {
        return  this.userRepository.findAll();
    }

    public void deleteUser(String id) {
        this.activityRepository.deleteById(id);
    }

    public List<note> listNote() {
        return  this.noteRepository.findAll();
    }
}

