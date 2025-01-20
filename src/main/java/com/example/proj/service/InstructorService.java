package com.example.proj.service;

import com.example.proj.dto.InstructorDTO;
import com.example.proj.dto.UserDTO;
import com.example.proj.model.Course;
import com.example.proj.model.Instructor;
import com.example.proj.repositry.InstructorRepositry;
import com.example.proj.utils.GenericObjectMapper;
import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

@Service("instructorService")
public class InstructorService implements UserService{

    @Autowired
    private InstructorRepositry instructorRepositry;

    public Page<InstructorDTO> getAllInstructors(int page, int size, String sortBy, String direction){
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(sortDirection, sortBy);  // Multiple fields can be added here
        Pageable pageable = PageRequest.of(page, size, sort);
        return instructorRepositry.findAll(pageable).map(this::mapToDTO);
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        InstructorDTO instructorDTO = (InstructorDTO) userDTO;
        Instructor instructor = GenericObjectMapper.map(instructorDTO,Instructor.class);
        instructor.setJoiningDate(Date.valueOf(LocalDate.now()));
        Instructor savedInstructor =  instructorRepositry.save(instructor);
        return mapToDTO(savedInstructor);
    }

    public InstructorDTO getInstructorById(long id){
        return instructorRepositry.findById(id).map(this::mapToDTO).orElse(null);
    }

    public Instructor getInstructor(long id){
        return instructorRepositry.findById(id).orElse(null);
    }

    private InstructorDTO mapToDTO(Instructor instructor) {
        InstructorDTO instructorDTO = GenericObjectMapper.map(instructor,InstructorDTO.class);
        instructorDTO.setCoursesId(Optional.ofNullable(instructor.getCourse())
                .map(courses -> courses.stream().map(Course::getId).toList())
                .orElse(Collections.emptyList()));
        return instructorDTO;
    }

}
