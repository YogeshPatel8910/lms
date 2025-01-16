package com.example.proj.service;

import com.example.proj.controller.StudentController;
import com.example.proj.dto.StudentDTO;
import com.example.proj.dto.UserDTO;
import com.example.proj.model.*;
import com.example.proj.repositry.StudentRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service("studentService")
public class StudentService implements UserService{

    @Autowired
    private StudentRepositry studentRepositry;

    @Autowired
    private CourseService courseService;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        Student student = new Student();
        StudentDTO studentDTO = (StudentDTO) userDTO;
        student.setName(studentDTO.getName());
        student.setEmail(studentDTO.getEmail());
        student.setPassword(studentDTO.getPassword());
        student.setRole(studentDTO.getRole());
        student.setEnrollmentDate(Date.valueOf(LocalDate.now()));
        Student savedUser =  studentRepositry.save(student);
        return mapToDTO(savedUser);

    }
    private StudentDTO mapToDTO(Student student) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setName(student.getName());
        studentDTO.setEmail(student.getEmail());
        studentDTO.setPassword(student.getPassword());
        studentDTO.setRole(student.getRole());
        studentDTO.setEnrollmentDate(student.getEnrollmentDate());
        studentDTO.setCourseId(Optional.ofNullable(student.getCourse()).map(courses -> courses.stream()
                                                                                                   .map(Course::getId)
                                                                                                   .toList())
                                                                                                   .orElse(Collections.emptyList()));
        studentDTO.setAssignmentId(student.getAssignment().stream().map(Assignment::getId).toList());
        studentDTO.setSubmissionId(student.getSubmission().stream().map(Submission::getId).toList());
        studentDTO.setResultId(student.getResult().stream().map(Result::getId).toList());
        return studentDTO;
    }

    public Page<StudentDTO> getAllStudent(int page, int size, String sortBy, String direction){
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(sortDirection, sortBy);  // Multiple fields can be added here
        Pageable pageRequest = PageRequest.of(page, size, sort);
        return studentRepositry.findAll(pageRequest).map(this::mapToDTO);
    }

    public Student getStudent(long id) {
        Optional<Student> byId = studentRepositry.findById(id);
        return byId.orElse(new Student());
    }

    public Page<StudentDTO> getStudentByCourse(long id, int page, int size, String sortBy, String direction) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(sortDirection, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return studentRepositry.findAllByCourseId(id,pageable).map(this::mapToDTO);
    }

    public StudentDTO getStudentById(long id) {
        return studentRepositry.findById(id).map(this::mapToDTO).orElse(null);
    }

    public StudentDTO updateStudent(long id, StudentDTO studentDTO) {
        Student student = studentRepositry.findById(id).orElse(null);
        if(student!=null){
            student.setName(studentDTO.getName());
            student.setEmail(studentDTO.getEmail());
            return mapToDTO(student);
        }
        else
            return null;
    }

    public StudentDTO enrollCourse(long studentId, long id) {
        Student student = studentRepositry.findById(studentId).orElse(null);
        Course course = courseService.getCourse(id);
        if(student!=null && course != null){
            List<Course> enroll = student.getCourse();
            enroll.add(course);
            student.setCourse(enroll);

            return mapToDTO(student);
        }
        else
            return null;
    }
}
