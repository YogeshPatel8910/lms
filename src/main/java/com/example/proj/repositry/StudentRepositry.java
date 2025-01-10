package com.example.proj.repositry;

import com.example.proj.model.Student;
import io.micrometer.observation.ObservationFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepositry extends JpaRepository<Student,Long> {
    Page<Student> findAllByCourseId(long id, Pageable pageable);
}
