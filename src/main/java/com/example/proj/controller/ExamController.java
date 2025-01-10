package com.example.proj.controller;

import com.example.proj.dto.ExamDTO;
import com.example.proj.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/exam")
public class ExamController {

    @Autowired
    private ExamService examService;

    @GetMapping
    public ResponseEntity<Page<ExamDTO>> getALlExam(@RequestParam(name = "page",required = false,defaultValue = "0")int page,
                                                    @RequestParam(name = "size",required = false,defaultValue = "10")int size,
                                                    @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                                                    @RequestParam(name = "direction", defaultValue = "asc") String direction) {
        return new ResponseEntity<>(examService.getAllExam(page,size,sortBy,direction), HttpStatus.OK);
    }

}
