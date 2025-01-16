package com.example.proj.repositry;

import com.example.proj.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepositry extends JpaRepository<Course,Long> {
    Page<Course> findAllByInstructorId(long id, Pageable pagable);
    Page<Course> findAllByStudentId(long id, Pageable pagable);
}
