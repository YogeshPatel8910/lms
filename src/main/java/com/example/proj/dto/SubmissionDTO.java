package com.example.proj.dto;

import lombok.Data;

@Data
public class SubmissionDTO {
    private Long id;

    private Long assignmentId;

    private Long studentId;

    private String submissionFile;

    private String grade;
}
