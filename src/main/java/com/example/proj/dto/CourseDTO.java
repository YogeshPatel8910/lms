package com.example.proj.dto;

import com.example.proj.model.*;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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

    private List<Assignment> assignment;

    private List<Exam> exam;

    private List<Student> student;

    private long instructorId;

    private long categoryId;

}
