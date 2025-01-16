package com.example.proj.service;

import com.example.proj.dto.CourseDTO;
import com.example.proj.model.*;
import com.example.proj.repositry.CourseRepositry;
import com.example.proj.utils.GenericObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

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
        return courseRepositry.findById(id).map(this::mapToDTO).orElse(null);
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
        Course course = GenericObjectMapper.map(courseDTO,Course.class);
        Instructor instructor = instructorService.getInstructor(id);
        Category category = categoryService.getCategoryById(courseDTO.getCategoryId());
        if(instructor==null||category==null){
            return null;
        }
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

    public Page<CourseDTO> getCourseByStudent(long id,int page, int size, String sortBy, String direction) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(sortDirection, sortBy);  // Multiple fields can be added here
        Pageable pageable = PageRequest.of(page, size, sort);
        return courseRepositry.findAllByStudentId(id,pageable).map(this::mapToDTO);
    }

    private CourseDTO mapToDTO(Course course) {
        CourseDTO courseDTO = GenericObjectMapper.map(course,CourseDTO.class);
        courseDTO.setLessonId(course.getLesson().stream().map(Lesson::getId).toList());
        courseDTO.setAssignmentId(course.getAssignment().stream().map(Assignment::getId).toList());
        courseDTO.setExamId(course.getExam().stream().map(Exam::getId).toList());
        courseDTO.setStudentId(course.getStudent().stream().map(Student::getId).toList());
        courseDTO.setInstructorId(course.getInstructor().getId());
        courseDTO.setCategoryId(course.getCategory().getId());
        return courseDTO;

    }

}
