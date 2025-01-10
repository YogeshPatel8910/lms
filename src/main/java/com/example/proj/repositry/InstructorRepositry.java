package com.example.proj.repositry;

import com.example.proj.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorRepositry extends JpaRepository<Instructor,Long> {
}
