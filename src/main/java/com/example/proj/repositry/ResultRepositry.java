package com.example.proj.repositry;

import com.example.proj.model.Result;
import com.example.proj.service.ResultService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultRepositry extends JpaRepository<Result, Long>{

    Page<Result> findAllByStudentId(long id, Pageable pageable);

    Result getResultByExamId(long id);
}
