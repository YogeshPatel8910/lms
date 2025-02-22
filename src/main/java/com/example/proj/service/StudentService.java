package com.example.proj.service;

import com.example.proj.dto.StudentDTO;
import com.example.proj.dto.UserDTO;
import com.example.proj.model.*;
import com.example.proj.repositry.StudentRepositry;
import com.example.proj.utils.GenericObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        StudentDTO studentDTO = (StudentDTO) userDTO;
        Student student = GenericObjectMapper.map(studentDTO,Student.class);
        student.setEnrollmentDate(Date.valueOf(LocalDate.now()));
        Student savedUser =  studentRepositry.save(student);
        return mapToDTO(savedUser);
    }

    public Page<StudentDTO> getAllStudent(int page, int size, String sortBy, String direction){
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(sortDirection, sortBy);  // Multiple fields can be added here
        Pageable pageRequest = PageRequest.of(page, size, sort);
        return studentRepositry.findAll(pageRequest).map(this::mapToDTO);
    }

    public Student getStudent(long id) {
        return studentRepositry.findById(id).orElse(null);
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

    private StudentDTO mapToDTO(Student student) {
        StudentDTO studentDTO = GenericObjectMapper.map(student,StudentDTO.class);
        studentDTO.setCourseId(Optional.ofNullable(student.getCourse())
                .map(courses -> courses.stream().map(Course::getId).toList())
                .orElse(Collections.emptyList()));
        studentDTO.setAssignmentId(Optional.ofNullable(student.getAssignment())
                .map(assignments -> assignments.stream().map(Assignment::getId).toList())
                .orElse(Collections.emptyList()));
        studentDTO.setSubmissionId(Optional.ofNullable(student.getSubmission())
                .map(submissions -> submissions.stream().map(Submission::getId).toList())
                .orElse(Collections.emptyList()));
        studentDTO.setResultId(Optional.ofNullable(student.getResult())
                .map(results -> results.stream().map(Result::getId).toList())
                .orElse(Collections.emptyList()));
        return studentDTO;
    }

}
