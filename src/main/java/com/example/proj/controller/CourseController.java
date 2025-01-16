package com.example.proj.controller;

import com.example.proj.dto.CourseDTO;
import com.example.proj.service.CourseService;
import com.example.proj.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private InstructorService instructorService;

    @GetMapping
    public ResponseEntity<Page<CourseDTO>> getAllCourse(@RequestParam(required = false) String filter,
                                                        @RequestParam(name = "page",required = false,defaultValue = "0")int page,
                                                        @RequestParam(name = "size",required = false,defaultValue = "10")int size,
                                                        @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                                                        @RequestParam(name = "direction", defaultValue = "asc") String direction) {
        return new ResponseEntity<>(courseService.getAllCourse(page,size,sortBy,direction), HttpStatus.OK);
    }


//    @DeleteMapping("admin/{id}")
//    public ResponseEntity<HttpStatus> deleteCourse(@PathVariable(name = "id") long id, @RequestBody CourseDTO courseDTO) {
//        boolean isDeleted = courseService.deleteCourse(id);
//        if (isDeleted) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } else
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }


}
