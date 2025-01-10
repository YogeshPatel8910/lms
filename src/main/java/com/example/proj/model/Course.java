package com.example.proj.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.persistence.criteria.Fetch;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.text.Format;
import java.util.List;

@Data
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Date createdDate;

    private Date updatedDate;

    @OneToMany(mappedBy = "course")
    private List<Lesson> lesson;

    @OneToMany(mappedBy = "course")
    private List<Assignment> assignment;

    @OneToMany(mappedBy = "course")
    private List<Exam> exam;

    @ManyToMany(mappedBy = "course")
    private List<Student> student;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "instructorId")
    private Instructor instructor;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId")
    private Category category;

}
