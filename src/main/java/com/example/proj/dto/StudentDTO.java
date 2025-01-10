package com.example.proj.dto;

import com.example.proj.model.Assignment;
import com.example.proj.model.Course;
import com.example.proj.model.Result;
import com.example.proj.model.Submission;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.sql.Date;
import java.util.List;

@Data
public class StudentDTO extends UserDTO {
        private Long id;

        private Date enrollmentDate;

        private List<Course> courseId;

        private List<Assignment> assignmentId;

        private List<Submission> submissionId;

        private List<Result> resultId;
}
