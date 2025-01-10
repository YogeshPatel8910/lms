package com.example.proj.service;

import com.example.proj.dto.InstructorDTO;
import com.example.proj.dto.UserDTO;
import com.example.proj.model.Course;
import com.example.proj.model.Instructor;
import com.example.proj.model.User;
import com.example.proj.repositry.InstructorRepositry;
import com.example.proj.repositry.UserRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
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
        Instructor instructor = new Instructor();
        InstructorDTO instructorDTO = (InstructorDTO) userDTO;
        instructor.setName(instructorDTO.getName());
        instructor.setEmail(instructorDTO.getEmail());
        instructor.setPassword(instructorDTO.getPassword());
        instructor.setRole(instructorDTO.getRole());
        instructor.setSpecialization(instructorDTO.getSpecialization());
        instructor.setJoiningDate(Date.valueOf(LocalDate.now()));
        Instructor savedInstructor =  instructorRepositry.save(instructor);
        return mapToDTO(savedInstructor);
    }

    private List<InstructorDTO> map(List<Instructor> instructors) {
        return instructors.stream().map(this::mapToDTO).toList();
    }

    private InstructorDTO mapToDTO(Instructor instructor) {
        InstructorDTO instructorDTO = new InstructorDTO();
        instructorDTO.setId(instructor.getId());
        instructorDTO.setName(instructor.getName());
        instructorDTO.setEmail(instructor.getEmail());
        instructorDTO.setRole(instructor.getRole());
        instructorDTO.setSpecialization(instructor.getSpecialization());
        instructorDTO.setJoiningDate(instructor.getJoiningDate());
        instructorDTO.setCoursesId(instructor.getCourse().stream().map(Course::getId).toList());
        return instructorDTO;
    }
    public InstructorDTO getInstructorById(long id){
        Optional<InstructorDTO> byId = instructorRepositry.findById(id).map(this::mapToDTO);
        return byId.orElse(null);
    }

    public Instructor getInstructor(long id){
        Optional<Instructor> byId = instructorRepositry.findById(id);
        return byId.orElse(null);
    }





//
//    private InstructorDTO mapToDTO(Instructor instructor) {
//        InstructorDTO instructorDTO = new InstructorDTO();
//        instructorDTO.setId(instructor.getId());
//        instructorDTO.setName(instructor.getName());
//        instructorDTO.setEmail(instructor.getEmail());
//        instructorDTO.setPassword(instructor.getPassword());
//        instructorDTO.setRole(instructor.getRole());
//        instructorDTO.setSpecialization(instructor.getSpecialization());
//        instructorDTO.setJoiningDate(instructor.getJoiningDate());
//        return instructorDTO;
//    }

}
