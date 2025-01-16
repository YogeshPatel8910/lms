package com.example.proj.controller;

import com.example.proj.dto.ResultDTO;
import com.example.proj.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/result")
public class ResultController {

    @Autowired
    private ResultService resultService;

//    @GetMapping("/student/{id}")
//    public ResponseEntity<ResultDTO> getAllResult(@PathVariable(name = "id") long id) {
//            return new ResponseEntity<>(resultService.getResultById(id),HttpStatus.NO_CONTENT);
//    }

//    @PostMapping("/instructor/{id}")
//    public ResponseEntity<ResultDTO> createResult(@PathVariable(name = "id") long id, @RequestBody ResultDTO resultDTO){
//            ResultDTO savedResult = resultService.createResult(id,resultDTO);
//            if(savedResult==null)
//                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//            else
//                return new ResponseEntity<>(savedResult,HttpStatus.CREATED);
//    }

//    @PutMapping("/instructor/{id}")
//    public ResponseEntity<Optional<ResultDTO>> updateResult(@PathVariable(name = "id") long id, @RequestBody ResultDTO resultDTO) {
//        Optional<ResultDTO> updateResult = resultService.updateResult(id, resultDTO);
//        if (updateResult.isPresent())
//            return new ResponseEntity<>(updateResult, HttpStatus.OK);
//        else
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//
//    }
//
//    @DeleteMapping("/instructor/{id}")
//    public ResponseEntity<HttpStatus> deleteResult(@PathVariable(name = "id") long id) {
//        boolean isDeleted = resultService.deleteResult(id);
//        if (isDeleted) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } else
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

}
