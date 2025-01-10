package com.example.proj.service;

import com.example.proj.dto.UserDTO;
import com.example.proj.model.ERole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class UserFactory {

    @Autowired
    @Qualifier("adminService")
    private AdminService adminService;

    @Autowired
    @Qualifier("studentService")
    private StudentService studentService;

    @Autowired
    @Qualifier("instructorService")
    private InstructorService instructorService;

    public UserService getService(ERole role) {
        return switch (role) {
            case ADMIN -> adminService;
            case STUDENT -> studentService;
            case INSTRUCTOR -> instructorService;
        };
    }
}
