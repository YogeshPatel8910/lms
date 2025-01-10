package com.example.proj.controller;

import com.example.proj.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/assignment")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;


}
