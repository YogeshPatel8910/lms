package com.example.proj.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "submissions")
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "assignmentId")
    private Assignment assignment;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "studentId")
    private Student student;

    private String submissionFile;

    private String grade;


}
