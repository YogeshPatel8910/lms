package com.example.proj.repositry;

import com.example.proj.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositry extends PagingAndSortingRepository<User,Long>,JpaRepository<User,Long> {
}
