package com.example.proj.controller;

import com.example.proj.dto.*;
import com.example.proj.model.Student;
import com.example.proj.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/instructor")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private LessonService lessonService;

    @Autowired
    private ExamService examService;

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private StudentService studentService;

//  instructor -- getAll
    @GetMapping
    public ResponseEntity<Page<InstructorDTO>> getAllUsers(@RequestParam(name = "page",required = false,defaultValue = "0")int page,
                                                           @RequestParam(name = "size",required = false,defaultValue = "10")int size,
                                                           @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                                                           @RequestParam(name = "direction", defaultValue = "asc") String direction) {
        return new ResponseEntity<>(instructorService.getAllInstructors(page,size,sortBy,direction), HttpStatus.OK);
    }

//  instructor -- get
    @GetMapping("/{id}")
    public ResponseEntity<InstructorDTO> getInstructorById(@PathVariable(name = "id") long id){
        return new ResponseEntity<>(instructorService.getInstructorById(id),HttpStatus.OK);
    }

//  course -- getAll
    @GetMapping("/{id}/course")
    public ResponseEntity<Page<CourseDTO>> getCourseByInstructor(@PathVariable(name = "id") long id,
                                                                 @RequestParam(name = "page",required = false,defaultValue = "0")int page,
                                                                 @RequestParam(name = "size",required = false,defaultValue = "10")int size,
                                                                 @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                                                                 @RequestParam(name = "direction", defaultValue = "asc") String direction){
        return new ResponseEntity<>(courseService.getCoursesOfInstructor(id,page,size,sortBy,direction), HttpStatus.OK);
    }

//  course -- get
    @GetMapping("/{instructorId}/course/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable(name = "id") long id){
        return new ResponseEntity<>(courseService.getCourseById(id),HttpStatus.OK);
    }

//  Course -- create
    @PostMapping("/{id}/course")
    public ResponseEntity<CourseDTO> createCourse(@PathVariable(name = "id") long id, @RequestBody CourseDTO courseDTO) {
        return new ResponseEntity<>(courseService.createCourse(id, courseDTO),HttpStatus.OK);
    }

//  Course -- update
    @PutMapping("/{instructorId}/course/{id}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable(name = "id") long id, @RequestBody CourseDTO courseDTO) {
        return new ResponseEntity<>(courseService.updateCourse(id, courseDTO), HttpStatus.OK);
    }

//  lesson -- getAll
    @GetMapping("/{instructorId}/course/{id}/lesson")
    public ResponseEntity<Page<LessonDTO>> getAllLesson(@RequestParam(name = "page",required = false,defaultValue = "0")int page,
                                                        @RequestParam(name = "size",required = false,defaultValue = "10")int size,
                                                        @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                                                        @RequestParam(name = "direction", defaultValue = "asc") String direction,
                                                        @PathVariable(name = "id") long id) {
        return new ResponseEntity<>(lessonService.getAllLessonByCourse(id,page,size,sortBy,direction), HttpStatus.OK);
    }

//  lesson -- get
    @GetMapping("/{instructorId}/course/{courseId}/lesson/{id}")
    public ResponseEntity<LessonDTO> getLessonById(@PathVariable(name = "id")long id){
        return new ResponseEntity<>(lessonService.getLessonById(id),HttpStatus.OK);
    }

//  lesson -- create
    @PostMapping("/{instructorId}/course/{id}/lesson")
    public ResponseEntity<LessonDTO> createLesson(@PathVariable(name = "id") long id, @RequestBody LessonDTO lessonDTO) {
            return new ResponseEntity<>(lessonService.createLesson(id, lessonDTO),HttpStatus.OK);
    }

//  lesson -- update
    @PutMapping("/{instructorId}/course/{courseId}/lesson/{id}")
    public ResponseEntity<LessonDTO> updateLesson(@PathVariable(name = "id") long id, @RequestBody LessonDTO lessonDTO) {
        return new ResponseEntity<>(lessonService.updateLesson(id, lessonDTO),HttpStatus.OK);
    }

//  lesson -- delete
    @DeleteMapping("/{instructorId}/course/{courseId}/lesson/{id}")
    public ResponseEntity<HttpStatus> deleteLesson(@PathVariable(name = "id") long id) {
        boolean isDeleted = lessonService.deleteLesson(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//  exam -- getAll
    @GetMapping("/{instructorId}/course/{id}/exams")
    public ResponseEntity<Page<ExamDTO>> getAllExam(@RequestParam(name = "page",required = false,defaultValue = "0")int page,
                                                    @RequestParam(name = "size",required = false,defaultValue = "10")int size,
                                                    @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                                                    @RequestParam(name = "direction", defaultValue = "asc") String direction,
                                                    @PathVariable(name = "id") long id) {
        return new ResponseEntity<>(examService.getAllExamByCourse(id,page,size,sortBy,direction), HttpStatus.OK);
    }

//   exam -- create
    @PostMapping("/{instructorId}/course/{id}/exam")
    public ResponseEntity<ExamDTO> createExam(@PathVariable(name = "id") long id, @RequestBody ExamDTO examDTO) {
        return new ResponseEntity<>(examService.createExam(id, examDTO),HttpStatus.OK);
    }

//  exam -- get
    @GetMapping("/{instructorId}/course/{courseId}/exam/{id}")
    public ResponseEntity<ExamDTO> getExamById(@PathVariable(name = "id")long id){
        return new ResponseEntity<>(examService.getExamById(id),HttpStatus.OK);
    }


//  exam -- update
    @PutMapping("/{instructorId}/course/{courseId}/exam/{id}")
    public ResponseEntity<ExamDTO> updateExam(@PathVariable(name = "id") long id, @RequestBody ExamDTO examDTO) {
        return new ResponseEntity<>(examService.updateExam(id, examDTO),HttpStatus.OK);
    }

//  exam -- delete
    @DeleteMapping("/{instructorId}/course/{courseId}/exam/{id}")
    public ResponseEntity<HttpStatus> deleteExam(@PathVariable(name = "id") long id) {
        boolean isDeleted = examService.deleteExam(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//  assignment -- getAll
    @GetMapping("/{instructorId}/course/{id}/assignments")
    public ResponseEntity<Page<AssignmentDTO>> getAllAssignment(@RequestParam(name = "page",required = false,defaultValue = "0")int page,
                                                              @RequestParam(name = "size",required = false,defaultValue = "10")int size,
                                                              @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                                                              @RequestParam(name = "direction", defaultValue = "asc") String direction,
                                                              @PathVariable(name = "id") long id) {
        return new ResponseEntity<>(assignmentService.getAllAssignmentByCourse(id,page,size,sortBy,direction), HttpStatus.OK);
    }

//  assignment -- getAssignmentById
    @GetMapping("/{instructorId}/course/{courseId}/assignments/{id}")
    public ResponseEntity<AssignmentDTO> getAllAssignmentById(@PathVariable(name = "id") long id) {
        return new ResponseEntity<>(assignmentService.getAssignmentById(id), HttpStatus.OK);
    }

//  assignment -- create
    @PostMapping("/{instructorId}/course/{id}/assignment")
    public ResponseEntity<AssignmentDTO> createAssignment(@PathVariable(name = "id") long id, @RequestBody AssignmentDTO assignmentDTO) {
        return new ResponseEntity<>(assignmentService.createAssignment(id, assignmentDTO),HttpStatus.OK);
    }

//  assignment -- update
    @PutMapping("/{instructorId}/course/{courseId}/assignment/{id}")
    public ResponseEntity<AssignmentDTO> updateAssignment(@PathVariable(name = "id") long id, @RequestBody AssignmentDTO assignmentDTO) {
        return new ResponseEntity<>(assignmentService.updateAssignment(id, assignmentDTO),HttpStatus.OK);
    }

//  assignment -- delete
    @DeleteMapping("/{instructorId}/course/{courseId}/assignment/{id}")
    public ResponseEntity<HttpStatus> deleteAssignment(@PathVariable(name = "id") long id) {
        boolean isDeleted = assignmentService.deleteAssignment(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//  submission -- getAll
    @GetMapping("/{instructorId}/course/{courseId}/assignment/{id}/submissions")
    public ResponseEntity<Page<SubmissionDTO>> getAllSubmission(@RequestParam(name = "page",required = false,defaultValue = "0")int page,
                                                                @RequestParam(name = "size",required = false,defaultValue = "10")int size,
                                                                @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                                                                @RequestParam(name = "direction", defaultValue = "asc") String direction,
                                                                @PathVariable(name = "id") long id) {
        return new ResponseEntity<>(submissionService.getAllSubmissionByAssignment(id,page,size,sortBy,direction), HttpStatus.OK);
    }
    
//  submission -- get
    @GetMapping("/{instructorId}/course/{courseId}/assignment/{assignmentId}/submissions/{id}")
    public ResponseEntity<SubmissionDTO> getSubmissionById(@PathVariable(name = "id")long id){
        return new ResponseEntity<>(submissionService.getSubmissionById(id),HttpStatus.OK);
    }

//  submission -- grade
    @PutMapping("/{instructorId}/course/{courseId}/assignment/{assignmentId}/submissions/{id}")
    public ResponseEntity<SubmissionDTO> gradeSubmission(@PathVariable(name = "id") long id, @RequestBody SubmissionDTO submissionDTO) {
        return new ResponseEntity<>(submissionService.gradeSubmission(id, submissionDTO),HttpStatus.OK);
    }

//  submission -- delete
    @DeleteMapping("/{instructorId}/course/{courseId}/assignment/{assignmentId}/submissions/{id}")
    public ResponseEntity<HttpStatus> deleteSubmission(@PathVariable(name = "id") long id) {
        boolean isDeleted = submissionService.deleteSubmission(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


//  student -- getAll
    @GetMapping("/{instructorId}/course/{id}/students")
    public ResponseEntity<Page<StudentDTO>> getAllStudent(@RequestParam(name = "page",required = false,defaultValue = "0")int page,
                                                           @RequestParam(name = "size",required = false,defaultValue = "10")int size,
                                                           @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                                                           @RequestParam(name = "direction", defaultValue = "asc") String direction,
                                                           @PathVariable(name = "id") long id) {
        return new ResponseEntity<>(studentService.getStudentByCourse(id,page,size,sortBy,direction), HttpStatus.OK);
    }

}
