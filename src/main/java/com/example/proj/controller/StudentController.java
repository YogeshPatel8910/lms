package com.example.proj.controller;

import com.example.proj.dto.CourseDTO;
import com.example.proj.dto.StudentDTO;
import com.example.proj.service.CourseService;
import com.example.proj.service.StudentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.JsonPath;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

//  student -- getAll
    @GetMapping
    public ResponseEntity<Page<StudentDTO>> getAllUsers(@RequestParam(name = "page",required = false,defaultValue = "0")int page,
                                                                           @RequestParam(name = "size",required = false,defaultValue = "10")int size,
                                                                           @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                                                                           @RequestParam(name = "direction", defaultValue = "asc") String direction) {
        return new ResponseEntity<>(studentService.getAllStudent(page,size,sortBy,direction), HttpStatus.OK);
    }

//  student -- getById
    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO>  getStudentById(@PathVariable(name = "id") long id){
        return new ResponseEntity<>(studentService.getStudentById(id),HttpStatus.OK);
    }

//  student --- update
    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable(name = "id") long id,
                                                    @RequestBody StudentDTO studentDTO){
        return new ResponseEntity<>(studentService.updateStudent(id,studentDTO), HttpStatus.OK);
    }

//  course -- getAll
    @GetMapping("/{id}/courses")
    public ResponseEntity<Page<CourseDTO>> getAllCourse(@PathVariable(name = "id") long id,
                                                                 @RequestParam(name = "page",required = false,defaultValue = "0")int page,
                                                                 @RequestParam(name = "size",required = false,defaultValue = "10")int size,
                                                                 @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                                                                 @RequestParam(name = "direction", defaultValue = "asc") String direction){
        return new ResponseEntity<>(courseService.getCourseByStudent(id,page,size,sortBy,direction), HttpStatus.OK);
    }

//  student -- allCourse
    @GetMapping("/{id}/enroll")
    public ResponseEntity<Page<CourseDTO>> getAllCourse(@RequestParam(name = "page",required = false,defaultValue = "0")int page,
                                                    @RequestParam(name = "size",required = false,defaultValue = "10")int size,
                                                    @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                                                    @RequestParam(name = "direction", defaultValue = "asc") String direction){
        return new ResponseEntity<>(courseService.getAllCourse(page, size, sortBy, direction), HttpStatus.OK);
    }

//  student -- enroll
    @Transactional
    @PostMapping("/{studentId}/enroll/{id}")
    public ResponseEntity<StudentDTO> enrollCourse(@PathVariable(name = "studentId") long studentId,
                                                    @PathVariable(name = "id") long id){
        return new ResponseEntity<>(studentService.enrollCourse(studentId,id),HttpStatus.OK);
    }

}
