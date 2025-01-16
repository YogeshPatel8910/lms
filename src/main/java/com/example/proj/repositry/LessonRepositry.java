package com.example.proj.repositry;

import com.example.proj.model.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepositry extends JpaRepository<Lesson,Long> {
    Page<Lesson> findAllByCourseId(long id, Pageable pageable);
}
