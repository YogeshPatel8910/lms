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
        Optional<InstructorDTO> instructor = Optional.ofNullable(instructorService.getInstructorById(id));
        return new ResponseEntity<>(instructor.get(),HttpStatus.OK);
    }

//  course -- getAll
    @GetMapping("/{id}/courses")
    public ResponseEntity<Page<CourseDTO>> getCourseByInstructor(@PathVariable(name = "id") long id,
                                                                 @RequestParam(name = "page",required = false,defaultValue = "0")int page,
                                                                 @RequestParam(name = "size",required = false,defaultValue = "10")int size,
                                                                 @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                                                                 @RequestParam(name = "direction", defaultValue = "asc") String direction){
        return new ResponseEntity<>(courseService.getCoursesOfInstructor(id,page,size,sortBy,direction), HttpStatus.OK);
    }

//  course -- get
    @GetMapping("/{Instructor_id}/course/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable(name = "id") long id){
        Optional<CourseDTO> course = Optional.ofNullable(courseService.getCourseById(id));
        return new ResponseEntity<>(course.get(),HttpStatus.OK);
    }

//  Course -- create
    @PostMapping("/{id}/course")
    public ResponseEntity<CourseDTO> createCourse(@PathVariable(name = "id") long id, @RequestBody CourseDTO courseDTO) {
        CourseDTO savedCourse = courseService.createCourse(id, courseDTO);
        if (savedCourse == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(savedCourse, HttpStatus.CREATED);
    }

//  Course -- update
    @PutMapping("/{Instructor_id}/course/{id}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable(name = "id") long id, @RequestBody CourseDTO courseDTO) {
        return new ResponseEntity<>(courseService.updateCourse(id, courseDTO), HttpStatus.OK);
    }

//  lesson -- getAll
    @GetMapping("/{Instructor_id}/course/{id}/lessons")
    public ResponseEntity<Page<LessonDTO>> getAllLesson(@RequestParam(name = "page",required = false,defaultValue = "0")int page,
                                                        @RequestParam(name = "size",required = false,defaultValue = "10")int size,
                                                        @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                                                        @RequestParam(name = "direction", defaultValue = "asc") String direction,
                                                        @PathVariable(name = "id") long id) {
        return new ResponseEntity<>(lessonService.getAllLessonByCourse(id,page,size,sortBy,direction), HttpStatus.OK);
    }

//  lesson -- create
    @PostMapping("/{Instructor_id}/course/{id}/lesson")
    public ResponseEntity<LessonDTO> createLesson(@PathVariable(name = "id") long id, @RequestBody LessonDTO lessonDTO) {
            LessonDTO savedLesson = lessonService.createLesson(id, lessonDTO);
            if (savedLesson == null)
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            else
                return new ResponseEntity<>(savedLesson, HttpStatus.CREATED);
    }

//  lesson -- update
    @PutMapping("/{instructorId}/course/{courseId}/lesson/{id}")
    public ResponseEntity<Optional<LessonDTO>> updateLesson(@PathVariable(name = "id") long id, @RequestBody LessonDTO lessonDTO) {
        Optional<LessonDTO> updateLesson = lessonService.updateLesson(id, lessonDTO);
        if (updateLesson.isPresent())
            return new ResponseEntity<>(updateLesson, HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
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
    @GetMapping("/{instructor_id}/course/{id}/exams")
    public ResponseEntity<Page<ExamDTO>> getAllExam(@RequestParam(name = "page",required = false,defaultValue = "0")int page,
                                                    @RequestParam(name = "size",required = false,defaultValue = "10")int size,
                                                    @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                                                    @RequestParam(name = "direction", defaultValue = "asc") String direction,
                                                    @PathVariable(name = "id") long id) {
        return new ResponseEntity<>(examService.getAllExamByCourse(id,page,size,sortBy,direction), HttpStatus.OK);
    }

//   exam -- create
    @PostMapping("/{instructor_id}/course/{id}/exam")
    public ResponseEntity<ExamDTO> createExam(@PathVariable(name = "id") long id, @RequestBody ExamDTO examDTO) {
        ExamDTO savedExam = examService.createExam(id, examDTO);
        if (savedExam == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(savedExam, HttpStatus.CREATED);
    }

//  exam -- update
    @PutMapping("/{instructor_id}/course/{courseId}/exam/{id}")
    public ResponseEntity<Optional<ExamDTO>> updateExam(@PathVariable(name = "id") long id, @RequestBody ExamDTO examDTO) {
        Optional<ExamDTO> updateExam = examService.updateExam(id, examDTO);
        if (updateExam.isPresent())
            return new ResponseEntity<>(updateExam, HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

//  exam -- delete
    @DeleteMapping("/{instructor_id}/course/{courseId}/exam/{id}")
    public ResponseEntity<HttpStatus> deleteExam(@PathVariable(name = "id") long id) {
        boolean isDeleted = examService.deleteExam(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//  assignment -- getAll
    @GetMapping("/{instructor_id}/course/{id}/assignments")
    public ResponseEntity<Page<AssignmentDTO>> getAllAssignment(@RequestParam(name = "page",required = false,defaultValue = "0")int page,
                                                              @RequestParam(name = "size",required = false,defaultValue = "10")int size,
                                                              @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                                                              @RequestParam(name = "direction", defaultValue = "asc") String direction,
                                                              @PathVariable(name = "id") long id) {
        return new ResponseEntity<>(assignmentService.getAllAssignmentByCourse(id,page,size,sortBy,direction), HttpStatus.OK);
    }

//  assignment -- create
    @PostMapping("/{instructor_id}/course/{id}/assignment")
    public ResponseEntity<AssignmentDTO> createAssignment(@PathVariable(name = "id") long id, @RequestBody AssignmentDTO assignmentDTO) {
        AssignmentDTO savedExam = assignmentService.createAssignment(id, assignmentDTO);
        if (savedExam == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(savedExam, HttpStatus.CREATED);
    }

//  assignment -- update
    @PutMapping("/{instructor_id}/course/{courseId}/assignment/{id}")
    public ResponseEntity<Optional<AssignmentDTO>> updateAssignment(@PathVariable(name = "id") long id, @RequestBody AssignmentDTO assignmentDTO) {
        Optional<AssignmentDTO> updateAssignment = assignmentService.updateAssignment(id, assignmentDTO);
        if (updateAssignment.isPresent())
            return new ResponseEntity<>(updateAssignment, HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

//  assignment -- delete
    @DeleteMapping("/{instructor_id}/course/{courseId}/assignment/{id}")
    public ResponseEntity<HttpStatus> deleteAssignment(@PathVariable(name = "id") long id) {
        boolean isDeleted = assignmentService.deleteAssignment(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//  submission -- getAll
    @GetMapping("/{instructor_id}/course/{courseId}/assignment/{id}/submissions")
    public ResponseEntity<Page<SubmissionDTO>> getAllSubmission(@RequestParam(name = "page",required = false,defaultValue = "0")int page,
                                                                @RequestParam(name = "size",required = false,defaultValue = "10")int size,
                                                                @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                                                                @RequestParam(name = "direction", defaultValue = "asc") String direction,
                                                                @PathVariable(name = "id") long id) {
        return new ResponseEntity<>(submissionService.getAllSubmissionByAssignment(id,page,size,sortBy,direction), HttpStatus.OK);
    }

//  submission -- update
    @PutMapping("/{instructor_id}/course/{courseId}/assignment/{assignmentId}/submissions/{id}")
    public ResponseEntity<Optional<SubmissionDTO>> updateAssignment(@PathVariable(name = "id") long id, @RequestBody SubmissionDTO submissionDTO) {
        Optional<SubmissionDTO> updateSubmission = submissionService.updateSubmission(id, submissionDTO);
        if (updateSubmission.isPresent())
            return new ResponseEntity<>(updateSubmission, HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

//  student -- getAll
    @GetMapping("/{instructor_id}/course/{id}/students")
    public ResponseEntity<Page<StudentDTO>> getAllStudent(@RequestParam(name = "page",required = false,defaultValue = "0")int page,
                                                           @RequestParam(name = "size",required = false,defaultValue = "10")int size,
                                                           @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                                                           @RequestParam(name = "direction", defaultValue = "asc") String direction,
                                                           @PathVariable(name = "id") long id) {
        return new ResponseEntity<>(studentService.getStudentByCourse(id,page,size,sortBy,direction), HttpStatus.OK);
    }

}
