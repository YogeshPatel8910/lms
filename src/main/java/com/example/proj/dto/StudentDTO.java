package com.example.proj.dto;

import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class StudentDTO extends UserDTO {
        private Long id;

        private Date enrollmentDate;

        private List<Long> courseId;

        private List<Long> assignmentId;

        private List<Long> submissionId;

        private List<Long> resultId;
}
