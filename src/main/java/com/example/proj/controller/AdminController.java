package com.example.proj.controller;

import com.example.proj.dto.CategoryDTO;
import com.example.proj.dto.CourseDTO;
import com.example.proj.dto.LessonDTO;
import com.example.proj.dto.UserDTO;
import com.example.proj.model.Category;
import com.example.proj.model.User;
import com.example.proj.service.AdminService;
import com.example.proj.service.CategoryService;
import com.example.proj.service.CourseService;
import com.example.proj.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private LessonService lessonService;

//  user -- getAll
    @GetMapping
    public ResponseEntity<Page<UserDTO>> getAllUsers(@RequestParam(name = "page",required = false,defaultValue = "0")int page,
                                                     @RequestParam(name = "size",required = false,defaultValue = "10")int size,
                                                     @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                                                     @RequestParam(name = "direction", defaultValue = "asc") String direction) {
        return new ResponseEntity<>(adminService.getUsers(page, size , sortBy , direction), HttpStatus.OK);
    }

//  user -- getById
    @GetMapping("/{id}")
    public  ResponseEntity<UserDTO> getUserById(@PathVariable(name = "id")long id){
        return new ResponseEntity<>(adminService.getUserById(id),HttpStatus.OK);
    }

//  user -- delete
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id) {
        if (adminService.deleteUser(id))
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//  category -- getAll
    @GetMapping("/category")
    public ResponseEntity<List<Category>> getAllCategory() {
        return new ResponseEntity<>(categoryService.getCategory(), HttpStatus.OK);
    }

//  category -- create
    @PostMapping("/category")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
        return new ResponseEntity<>(categoryService.createCategory(categoryDTO), HttpStatus.CREATED);
    }

//  category -- update
    @PutMapping("/category/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable(name = "id") long id, @RequestBody CategoryDTO categoryDTO) {
        return new ResponseEntity<>(categoryService.updateCategory(id, categoryDTO),HttpStatus.OK);
    }

//  category -- delete
    @DeleteMapping("/category/{id}")
    public ResponseEntity<HttpStatus> deleteCategory(@PathVariable("id") long id) {
        if (categoryService.deleteCategory(id))
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

//  course -- getAll
    @GetMapping("/course")
    public ResponseEntity<Page<CourseDTO>> getCourseByInstructor(@RequestParam(name = "page",required = false,defaultValue = "0")int page,
                                                                 @RequestParam(name = "size",required = false,defaultValue = "10")int size,
                                                                 @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                                                                 @RequestParam(name = "direction", defaultValue = "asc") String direction){
        return new ResponseEntity<>(courseService.getAllCourse(page,size,sortBy,direction), HttpStatus.OK);
    }
//  course -- get
    @GetMapping("/course/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable(name = "id") long id){
        return new ResponseEntity<>(courseService.getCourseById(id),HttpStatus.OK);
    }

//  course -- delete
    @DeleteMapping("/course/{id}")
    public ResponseEntity<HttpStatus> deleteCourse(@PathVariable(name = "id") long id, @RequestBody CourseDTO courseDTO) {
        boolean isDeleted = courseService.deleteCourse(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//  lesson -- getAll
    @GetMapping("/course/{id}/lesson")
    public ResponseEntity<Page<LessonDTO>> getAllLesson(@RequestParam(name = "page",required = false,defaultValue = "0")int page,
                                                        @RequestParam(name = "size",required = false,defaultValue = "10")int size,
                                                        @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                                                        @RequestParam(name = "direction", defaultValue = "asc") String direction,
                                                        @PathVariable(name = "id") long id) {
        return new ResponseEntity<>(lessonService.getAllLessonByCourse(id,page,size,sortBy,direction), HttpStatus.OK);
    }

//  lesson -- get
    @GetMapping("/course/{courseId}/lesson/{id}")
    public ResponseEntity<LessonDTO> getLessonById(@PathVariable(name = "id")long id){
        return new ResponseEntity<>(lessonService.getLessonById(id),HttpStatus.OK);
    }


}
