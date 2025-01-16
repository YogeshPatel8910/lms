package com.example.proj.dto;

import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class CourseDTO {
    private Long id;

    private String name;

    private String description;

    private Date createdDate;

    private Date updatedDate;

    private List<Long> lessonId;

    private List<Long> assignmentId;

    private List<Long> examId;

    private List<Long> studentId;

    private long instructorId;

    private long categoryId;

}
