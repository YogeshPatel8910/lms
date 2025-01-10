package com.example.proj.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
@Entity
@DiscriminatorValue("student")
@Table(name = "students")
public class Student extends User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date enrollmentDate;

    @JsonIgnore
    @OneToMany(mappedBy = "student")
    private List<Submission> submission;

    @JsonIgnore
    @OneToMany(mappedBy = "student")
    private List<Result> result;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "enrollments",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id"))
    private List<Course> course;

    @JsonIgnore
    @ManyToMany(mappedBy = "student")
    private List<Assignment> assignment;

}
