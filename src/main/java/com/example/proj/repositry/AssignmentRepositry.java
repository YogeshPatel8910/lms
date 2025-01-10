package com.example.proj.repositry;

import com.example.proj.model.Assignment;
import io.micrometer.observation.ObservationFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentRepositry extends JpaRepository<Assignment,Long> {
    Page<Assignment> findAllByCourseId(long id, Pageable pageable);
}
