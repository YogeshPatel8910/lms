package com.example.proj.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "exams")
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Date date;

    private int duration;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "courseId")
    private Course course;

    @JsonIgnore
    @OneToMany(mappedBy = "exam")
    private List<Result> result;

}
