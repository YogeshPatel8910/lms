package com.example.proj.service;

import com.example.proj.dto.AssignmentDTO;
import com.example.proj.model.Assignment;
import com.example.proj.model.Course;
import com.example.proj.model.Student;
import com.example.proj.model.Submission;
import com.example.proj.repositry.AssignmentRepositry;
import com.example.proj.utils.GenericObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class AssignmentService {

    @Autowired
    private AssignmentRepositry assignmentRepositry;

    @Autowired
    private CourseService courseService;

    public AssignmentDTO getAssignmentById(long id) {
        return assignmentRepositry.findById(id).map(this::mapToDTO).orElse(null);
    }
    public Assignment getAssignment(long id){
        return assignmentRepositry.findById(id).orElse(null);
    }

    public Page<AssignmentDTO> getAllAssignmentByCourse(long id, int page, int size, String sortBy, String direction) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(sortDirection, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return assignmentRepositry.findAllByCourseId(id,pageable).map(this::mapToDTO);
    }

    public AssignmentDTO createAssignment(long id, AssignmentDTO assignmentDTO) {
        Course course = courseService.getCourse(id);
        Assignment assignment = GenericObjectMapper.map(assignmentDTO,Assignment.class);
        assignment.setCourse(course);
        Assignment savedAssignment = assignmentRepositry.save(assignment);
        return mapToDTO(savedAssignment);
    }

    public AssignmentDTO updateAssignment(long id, AssignmentDTO assignmentDTO) {
        Assignment assignment = assignmentRepositry.findById(id).orElse(null);
        if(assignment!=null){
            assignment.setTitle(assignmentDTO.getTitle());
            assignment.setDescription(assignmentDTO.getDescription());
            assignment.setDeadline(assignmentDTO.getDeadline());
            return mapToDTO(assignment);
        }
        else
            return null;
    }

    public boolean deleteAssignment(long id) {
        boolean isPresent = assignmentRepositry.existsById(id);
        if(isPresent){
            assignmentRepositry.deleteById(id);
            return true;
        }
        else
            return false;
    }

    public Page<AssignmentDTO> getAssignmentByStudent(long id, int page, int size, String sortBy, String direction) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(sortDirection, sortBy);  // Multiple fields can be added here
        Pageable pageable = PageRequest.of(page, size, sort);
        return assignmentRepositry.findAllByStudentId(id,pageable).map(this::mapToDTO);
    }

    private AssignmentDTO mapToDTO(Assignment assignment) {
        AssignmentDTO assignmentDTO = GenericObjectMapper.map(assignment,AssignmentDTO.class);
        assignmentDTO.setCourseId(assignment.getCourse().getId());
        assignmentDTO.setSubmissionId(assignment.getSubmission().stream().map(Submission::getId).toList());
        assignmentDTO.setStudentId(assignment.getStudent().stream().map(Student::getId).toList());
        return assignmentDTO;
    }

}
