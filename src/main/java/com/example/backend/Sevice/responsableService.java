package com.example.backend.Sevice;

import com.example.backend.DTO.ChildDto;
import com.example.backend.repository.ChildRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.repository.groupRepository;
import com.example.backend.schema.User;
import com.example.backend.schema.child;
import com.example.backend.schema.group;
import com.example.backend.schema.groupDTO;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class responsableService {
    private final ChildRepository childRepository;
    private final UserRepository repository;
    private final groupRepository groupRepository;

    public responsableService(ChildRepository childRepository, UserRepository repository, groupRepository groupRepository) {
        this.childRepository = childRepository;
        this.repository = repository;
        this.groupRepository = groupRepository;
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
    public String addChildToGrp(child childId, User userId, groupDTO request) {
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
            newGroup.setUser(newUser);
            newGroup.getChildren().add(newChild);// Add the new child to the group's children list
            newGroup.setNameG(request.getNameG());
            this.groupRepository.save(newGroup); // Save the updated group to the repository or database
        }
        return "Group added successfully";
    }
    public group getGroupById(String id) {
        
    Optional<group> gp = this.groupRepository.findById(id);
    return gp.isPresent() ? gp.get(): null;
    
    }
}

