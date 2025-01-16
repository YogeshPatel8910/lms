package com.example.proj.controller;

import com.example.proj.dto.*;
import com.example.proj.service.*;
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

import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private LessonService lessonService;

    @Autowired
    private ExamService examService;

    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private ResultService resultService;

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


//  student -- allCourse
    @GetMapping("/{id}/enroll")
    public ResponseEntity<Page<CourseDTO>> showCourses(@RequestParam(name = "page",required = false,defaultValue = "0")int page,
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

//  course -- getAll
    @GetMapping("/{id}/courses")
    public ResponseEntity<Page<CourseDTO>> getAllCourse(@PathVariable(name = "id") long id,
                                                                 @RequestParam(name = "page",required = false,defaultValue = "0")int page,
                                                                 @RequestParam(name = "size",required = false,defaultValue = "10")int size,
                                                                 @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                                                                 @RequestParam(name = "direction", defaultValue = "asc") String direction){
        return new ResponseEntity<>(courseService.getCourseByStudent(id,page,size,sortBy,direction), HttpStatus.OK);
    }

//  course -- get
    @GetMapping("/{studentId}/course/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable(name = "id") long id){
        return new ResponseEntity<>(courseService.getCourseById(id),HttpStatus.OK);
    }

//  lesson -- getAll
    @GetMapping("/{studentId}/course/{id}/lessons")
    public ResponseEntity<Page<LessonDTO>> getAllLesson(@RequestParam(name = "page",required = false,defaultValue = "0")int page,
                                                        @RequestParam(name = "size",required = false,defaultValue = "10")int size,
                                                        @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                                                        @RequestParam(name = "direction", defaultValue = "asc") String direction,
                                                        @PathVariable(name = "id") long id) {
        return new ResponseEntity<>(lessonService.getAllLessonByCourse(id,page,size,sortBy,direction), HttpStatus.OK);
    }

//  lesson -- get
    @GetMapping("/{studentId}/course/{courseId}/lesson/{id}")
    public ResponseEntity<LessonDTO> getLessonById(@PathVariable(name = "id")long id){
        return new ResponseEntity<>(lessonService.getLessonById(id),HttpStatus.OK);
    }

//  exam -- getAll
    @GetMapping("/{studentId}/course/{id}/exams")
    public ResponseEntity<Page<ExamDTO>> getAllExam(@RequestParam(name = "page",required = false,defaultValue = "0")int page,
                                                    @RequestParam(name = "size",required = false,defaultValue = "10")int size,
                                                    @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                                                    @RequestParam(name = "direction", defaultValue = "asc") String direction,
                                                    @PathVariable(name = "id") long id) {
        return new ResponseEntity<>(examService.getAllExamByCourse(id,page,size,sortBy,direction), HttpStatus.OK);
    }

//  result -- getByExamId
    @GetMapping("/{studentId}/course/{courseId}/exam/{id}")
    public ResponseEntity<ResultDTO> getResultByExamId(@PathVariable(name = "id")long id){
        return new ResponseEntity<>(resultService.getResultByExam(id),HttpStatus.OK);
    }


//  assignment -- getAll(byCourse)
    @GetMapping("/{studentId}/course/{id}/assignments")
    public ResponseEntity<Page<AssignmentDTO>> getAllAssignmentByCourseId(@PathVariable(name = "id") long id,
                                                                @RequestParam(name = "page",required = false,defaultValue = "0")int page,
                                                                @RequestParam(name = "size",required = false,defaultValue = "10")int size,
                                                                @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                                                                @RequestParam(name = "direction", defaultValue = "asc") String direction) {
        return new ResponseEntity<>(assignmentService.getAllAssignmentByCourse(id,page,size,sortBy,direction), HttpStatus.OK);
    }


//  assignment -- getAll
    @GetMapping("/{id}/assignments")
    public ResponseEntity<Page<AssignmentDTO>> getAllAssignmentByStudentId(@PathVariable(name = "id")long id,
                                                         @RequestParam(name = "page",required = false,defaultValue = "0")int page,
                                                         @RequestParam(name = "size",required = false,defaultValue = "10")int size,
                                                         @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                                                         @RequestParam(name = "direction", defaultValue = "asc") String direction){
        return new ResponseEntity<>(assignmentService.getAssignmentByStudent(id, page, size, sortBy, direction), HttpStatus.OK);
    }


//  assignment -- getAsssignmentById
    @GetMapping(value = {"/{studentId}/course/{courseId}/assignments/{id}","/{studentId}/assignments/{id}"})
    public ResponseEntity<AssignmentDTO> getAllAssignmentById(@PathVariable(name = "id") long id) {
        return new ResponseEntity<>(assignmentService.getAssignmentById(id), HttpStatus.OK);
    }


//  submission -- getAll
    @GetMapping("/{id}/submissions")
    public ResponseEntity<Page<SubmissionDTO>> getAllSubmission(@RequestParam(name = "page",required = false,defaultValue = "0")int page,
                                                                @RequestParam(name = "size",required = false,defaultValue = "10")int size,
                                                                @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                                                                @RequestParam(name = "direction", defaultValue = "asc") String direction,
                                                                @PathVariable(name = "id") long id) {
        return new ResponseEntity<>(submissionService.getAllSubmissionByAssignment(id,page,size,sortBy,direction), HttpStatus.OK);
    }

//  submission -- get
    @GetMapping(value = {"/{studentId}/course/{courseId}/assignment/{assignmentId}/submissions/{id}",
                         "/{studentId}/assignment/{assignmentId}/submission/{id}",
                         "/{studentId}/submission/{id}"})
    public ResponseEntity<SubmissionDTO> getSubmissionById(@PathVariable(name = "id")long id){
        return new ResponseEntity<>(submissionService.getSubmissionById(id),HttpStatus.OK);
    }

//  submission -- create
    @PostMapping(value = {"/{studentId}/course/{courseId}/assignment/{assignmentId}/submissions/{id}",
                         "/{studentId}/assignment/{assignmentId}/submission/{id}",
                         "/{studentId}/submission/{id}"})
    public ResponseEntity<SubmissionDTO> createSubmission(@PathVariable(name = "id")long id,
                                                       @RequestBody SubmissionDTO submissionDTO){
        return new ResponseEntity<>(submissionService.createSubmission(id,submissionDTO),HttpStatus.OK);
    }

//  submission -- update
    @PutMapping(value = {"/{studentId}/course/{courseId}/assignment/{assignmentId}/submissions/{id}",
                         "/{studentId}/assignment/{assignmentId}/submission/{id}",
                         "/{studentId}/submission/{id}"})
    public ResponseEntity<SubmissionDTO> updateAssignment(@PathVariable(name = "id") long id, @RequestBody SubmissionDTO submissionDTO) {
        return new ResponseEntity<>(submissionService.updateSubmission(id, submissionDTO),HttpStatus.OK);
    }

//  submission -- delete
    @DeleteMapping(value = {"/{studentId}/course/{courseId}/assignment/{assignmentId}/submissions/{id}",
                            "/{studentId}/assignment/{assignmentId}/submission/{id}",
                            "/{studentId}/submission/{id}"})
    public ResponseEntity<HttpStatus> deleteAssignment(@PathVariable(name = "id")long id){
        boolean isDeleted = submissionService.deleteSubmission(id);
        if(isDeleted)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//  result -- getAll
    @GetMapping("/{id}/result")
    public ResponseEntity<Page<ResultDTO>> getResultByStudentId(@RequestParam(name = "page",required = false,defaultValue = "0")int page,
                                                        @RequestParam(name = "size",required = false,defaultValue = "10")int size,
                                                        @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                                                        @RequestParam(name = "direction", defaultValue = "asc") String direction,
                                                        @PathVariable(name = "id") long id) {
        return new ResponseEntity<>(resultService.getResultByStudent(id,page,size,sortBy,direction),HttpStatus.OK);
    }

//  result -- get
    @GetMapping("/{studentId}/result/{id}")
    public ResponseEntity<ResultDTO> getResultByID(@PathVariable(name = "id")long id){
        return new ResponseEntity<>(resultService.getResultById(id),HttpStatus.OK);
    }


}
