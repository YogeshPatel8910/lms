package com.example.proj.repositry;

import com.example.proj.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepositry extends JpaRepository<Category, Long>{

}
