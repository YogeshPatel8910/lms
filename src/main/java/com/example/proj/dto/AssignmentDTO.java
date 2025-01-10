package com.example.proj.dto;

import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class AssignmentDTO {
    private Long id;

    private String title;

    private String description;

    private Date deadline;

    private Long courseId;

    private List<Long> submissionId;

    private List<Long> studentId;

}
