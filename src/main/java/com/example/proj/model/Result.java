package com.example.proj.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;

@Data
@Entity
@Table(name = "results")
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "examId")
    private Exam exam;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "studentId")
    private  Student student;

    private Integer marksObtained;

}
