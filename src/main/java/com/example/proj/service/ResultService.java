package com.example.proj.service;

import com.example.proj.dto.ResultDTO;
import com.example.proj.dto.ResultDTO;
import com.example.proj.model.Course;
import com.example.proj.model.Result;
import com.example.proj.repositry.ResultRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResultService {
    
    @Autowired
    private ResultRepositry resultRepositry;

    public ResultDTO getResultById(long id) {
        return resultRepositry.findById(id).map(this::mapToDTO).orElse(null);
    }

//    public ResultDTO createResult(long id, ResultDTO resultDTO) {
//        Course course = courseService.getCourseById(id);
//        Result result = new Result();
//        result.setTitle(resultDTO.getTitle());
//        result.setContent(resultDTO.getContent());
//        result.setDuration(resultDTO.getDuration());
//        result.setCourse(course);
//        Result saved = resultRepositry.save(result);
//        return mapToDTO(saved);
//    }

    public Optional<ResultDTO> updateResult(long id, ResultDTO resultDTO) {
        Optional<Result> result = resultRepositry.findById(id);
        if(result.isPresent()){
            Result recieved = result.get();
            recieved.setMarksObtained(resultDTO.getMarksObtained());
            return Optional.of(mapToDTO(recieved));
        }
        else
            return Optional.empty();
    }

    public boolean deleteResult(long id) {
        boolean isPresent = resultRepositry.existsById(id);
        if(isPresent){
            resultRepositry.deleteById(id);
            return true;
        }
        else
            return false;
    }


    private ResultDTO mapToDTO(Result result) {
        ResultDTO resultDTO =new ResultDTO();
        resultDTO.setId(result.getId());
        resultDTO.setExamId(result.getExam().getId());
        resultDTO.setStudentId(result.getStudent().getId());
        resultDTO.setMarksObtained(result.getMarksObtained());
        return resultDTO;
    }

    public Page<ResultDTO> getResultByStudent(long id,int page, int size, String sortBy, String direction) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(sortDirection, sortBy);  // Multiple fields can be added here
        Pageable pageable = PageRequest.of(page, size, sort);
        return resultRepositry.findAllByStudentId(id,pageable).map(this::mapToDTO);
    }

    public ResultDTO getResultByExam(long id) {
        return mapToDTO(resultRepositry.getResultByExamId(id));
    }
}
