package com.example.proj.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
@Entity
@DiscriminatorValue("instructor")
@Table(name = "instructors")
@JsonIgnoreProperties(value = "instructor")
public class Instructor extends User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String specialization;

    private Date joiningDate;

    @JsonIgnore
    @OneToMany(mappedBy = "instructor")
    private List<Course> course;


}
