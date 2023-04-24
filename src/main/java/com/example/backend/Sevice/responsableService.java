package com.example.backend.Sevice;

import com.example.backend.DTO.ChildDto;
import com.example.backend.repository.ChildRepository;
import com.example.backend.schema.child;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class responsableService {
    @Autowired
    private  ChildRepository childRepository;
    public child addingChild(ChildDto request) {
        child newChild = new child();
        newChild.setName(request.getName());
        newChild.setLastName(request.getLastName());
        newChild.setAge(request.getAge());
        newChild.setBirthDate(request.getBirthDate());
        return childRepository.save(newChild);
    }
}

