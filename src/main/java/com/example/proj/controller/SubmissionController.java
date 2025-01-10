package com.example.proj.controller;

import com.example.proj.dto.SubmissionDTO;
import com.example.proj.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/submission")
public class SubmissionController {

    @Autowired
    private SubmissionService submissionService;

    @GetMapping
    public ResponseEntity<Page<SubmissionDTO>> getALlSubmission(@RequestParam(name = "page",required = false,defaultValue = "0")int page,
                                                                @RequestParam(name = "size",required = false,defaultValue = "10")int size,
                                                                @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                                                                @RequestParam(name = "direction", defaultValue = "asc") String direction) {
        return new ResponseEntity<>(submissionService.getAllSubmission(page,size,sortBy,direction), HttpStatus.OK);
    }

    @PostMapping("/student/{id}")
    public ResponseEntity<SubmissionDTO> createSubmission(@PathVariable(name = "id") long id, @RequestBody SubmissionDTO submissionDTO) {
        SubmissionDTO savedSubmission = submissionService.createSubmission(id, submissionDTO);
        if (savedSubmission == null)
            return new ResponseEntity<>(savedSubmission, HttpStatus.CREATED);
        else
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

    }

    @PutMapping("/instructor/{id}")
    public ResponseEntity<Optional<SubmissionDTO>> updateSubmission(@PathVariable(name = "id") long id, @RequestBody SubmissionDTO submissionDTO) {
        Optional<SubmissionDTO> updateSubmission = submissionService.updateSubmission(id, submissionDTO);
        if (updateSubmission.isPresent())
            return new ResponseEntity<>(updateSubmission, HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<HttpStatus> deleteSubmission(@PathVariable(name = "id") long id) {
        boolean isDeleted = submissionService.deleteSubmission(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
