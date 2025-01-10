package com.example.proj.controller;

import com.example.proj.dto.LessonDTO;
import com.example.proj.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/lesson")
public class LessonController {

    @Autowired
    private LessonService lessonService;



}
