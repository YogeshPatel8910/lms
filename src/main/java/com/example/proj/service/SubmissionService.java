package com.example.proj.service;

import com.example.proj.dto.LessonDTO;
import com.example.proj.dto.SubmissionDTO;
import com.example.proj.model.Assignment;
import com.example.proj.model.Lesson;
import com.example.proj.model.Student;
import com.example.proj.model.Submission;
import com.example.proj.repositry.StudentRepositry;
import com.example.proj.repositry.SubmissionRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubmissionService {

    @Autowired
    private SubmissionRepositry submissionRepositry;

    @Autowired
    private StudentService studentService;

    @Autowired
    private AssignmentService assignmentService;

    public Page<SubmissionDTO> getAllSubmission(int page, int size, String sortBy, String direction) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(sortDirection, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return submissionRepositry.findAll(pageable).map(this::mapToDTO);
    }

    public SubmissionDTO createSubmission(long id, SubmissionDTO submissionDTO) {
        Student student = studentService.getStudent(id);
        Assignment assignment = assignmentService.getAssignment(id);
        Submission submission = new Submission();
        submission.setStudent(student);
        submission.setAssignment(assignment);
        submission.setSubmissionFile(submissionDTO.getSubmissionFile());
        Submission savedSubmission = submissionRepositry.save(submission);
        return mapToDTO(savedSubmission);
    }

    public Optional<SubmissionDTO> gradeSubmission(long id, SubmissionDTO submissionDTO) {
        Optional<Submission> submission = submissionRepositry.findById(id);
        if(submission.isPresent()){
            Submission recieved = submission.get();
            recieved.setGrade(submissionDTO.getGrade());
            return Optional.of(mapToDTO(recieved));
        }
        else
            return Optional.empty();
    }

    public Optional<SubmissionDTO> updateSubmission(long id, SubmissionDTO submissionDTO) {
        Optional<Submission> submission = submissionRepositry.findById(id);
        if(submission.isPresent()){
            Submission recieved = submission.get();
            recieved.setGrade(submissionDTO.getGrade());
            return Optional.of(mapToDTO(recieved));
        }
        else
            return Optional.empty();
    }

    public boolean deleteSubmission(long id) {
        boolean isPresent = submissionRepositry.existsById(id);
        if(isPresent){
            submissionRepositry.deleteById(id);
            return true;
        }
        else
            return false;
    }

    private SubmissionDTO mapToDTO(Submission submission){
        SubmissionDTO submissionDTO = new SubmissionDTO();
        submissionDTO.setId(submission.getId());
        submissionDTO.setAssignmentId(submission.getAssignment().getId());
        submissionDTO.setStudentId(submission.getStudent().getId());
        submissionDTO.setSubmissionFile(submission.getSubmissionFile());
        submissionDTO.setGrade(submission.getGrade());
        return submissionDTO;
    }

    public Page<SubmissionDTO> getAllSubmissionByAssignment(long id, int page, int size, String sortBy, String direction) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(sortDirection, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return submissionRepositry.findAllByAssignmentId(id,pageable).map(this::mapToDTO);
    }

    public SubmissionDTO getSubmissionById(long id) {
        return submissionRepositry.findById(id).map(this::mapToDTO).orElse(null);
    }
}
