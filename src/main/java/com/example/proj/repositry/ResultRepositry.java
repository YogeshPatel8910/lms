package com.example.proj.repositry;

import com.example.proj.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultRepositry extends JpaRepository<Result, Long>{

        }
