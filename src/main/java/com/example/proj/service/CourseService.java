package com.example.proj.service;

import com.example.proj.dto.AssignmentDTO;
import com.example.proj.dto.CategoryDTO;
import com.example.proj.dto.CourseDTO;
import com.example.proj.model.*;
import com.example.proj.repositry.CourseRepositry;
import com.example.proj.repositry.UserRepositry;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@Service
public class CourseService {

    @Autowired
    private CourseRepositry courseRepositry;

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private CategoryService categoryService;


    public Page<CourseDTO> getAllCourse(int page, int size, String sortBy, String direction) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(sortDirection, sortBy);  // Multiple fields can be added here
        Pageable pageable = PageRequest.of(page, size, sort);
        return courseRepositry.findAll(pageable).map(this::mapToDTO);
    }

    public CourseDTO getCourseById(long id){
        Optional<CourseDTO> byId=courseRepositry.findById(id).map(this::mapToDTO);
        return byId.orElse(null);
    }

    public Course getCourse(long id) {
        return courseRepositry.findById(id).orElse(null);
    }

    public Page<CourseDTO> getCoursesOfInstructor(long id, int page, int size, String sortBy, String direction) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(sortDirection, sortBy);  // Multiple fields can be added here
        Pageable pageable = PageRequest.of(page, size, sort);
        return courseRepositry.findAllByInstructorId(id,pageable).map(this::mapToDTO);
    }

    public CourseDTO createCourse(long id, CourseDTO courseDTO) {
        Course course = new Course();
        Instructor instructor = instructorService.getInstructor(id);
        Category category = categoryService.getCategoryById(courseDTO.getCategoryId());
        if(instructor==null||category==null){
            return null;
        }
        course.setName(courseDTO.getName());
        course.setDescription(courseDTO.getDescription());
        course.setCreatedDate(Date.valueOf(LocalDate.now()));
        course.setInstructor(instructor);
        course.setCategory(category);
        Course savedCourse = courseRepositry.save(course);
        return mapToDTO(savedCourse);
    }

    public CourseDTO updateCourse(long id, CourseDTO courseDTO) {
        Course course = courseRepositry.findById(id).orElse(null);
        if (course != null){
            course.setName(courseDTO.getName());
            course.setDescription(courseDTO.getDescription());
            course.setUpdatedDate(Date.valueOf(LocalDate.now()));
            return mapToDTO(course);
        }else
            return null;
    }

    public boolean deleteCourse(long id) {
        boolean isPresent = courseRepositry.existsById(id);
        if(isPresent){
            courseRepositry.deleteById(id);
            return true;
        }
        else
            return false;
    }


    private CourseDTO mapToDTO(Course course) {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(course.getId());
        courseDTO.setName(course.getName());
        courseDTO.setDescription(course.getDescription());
        courseDTO.setCreatedDate(course.getCreatedDate());
        courseDTO.setLessonId(course.getLesson().stream().map(Lesson::getId).toList());
        courseDTO.setAssignment(course.getAssignment());
        courseDTO.setExam(course.getExam());
        courseDTO.setStudent(course.getStudent());
        courseDTO.setInstructorId(course.getInstructor().getId());
        courseDTO.setCategoryId(course.getCategory().getId());
        return courseDTO;

    }


    public Page<CourseDTO> getCourseByStudent(long id,int page, int size, String sortBy, String direction) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(sortDirection, sortBy);  // Multiple fields can be added here
        Pageable pageable = PageRequest.of(page, size, sort);
        return courseRepositry.findAllByStudentId(id,pageable).map(this::mapToDTO);
    }

}
