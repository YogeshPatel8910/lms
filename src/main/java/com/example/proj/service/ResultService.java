package com.example.proj.service;

import com.example.proj.dto.ResultDTO;
import com.example.proj.dto.ResultDTO;
import com.example.proj.model.Course;
import com.example.proj.model.Result;
import com.example.proj.repositry.ResultRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResultService {
    
    @Autowired
    private ResultRepositry resultRepositry;

    public Optional<ResultDTO> getResultById(long id) {
        return resultRepositry.findById(id).map(this::mapToDTO);
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



    private List<ResultDTO> map(List<Result> results) {
        return results.stream().map(this::mapToDTO).toList();
    }

    private ResultDTO mapToDTO(Result result) {
        ResultDTO resultDTO =new ResultDTO();
        resultDTO.setId(result.getId());
        resultDTO.setExamId(result.getExam().getId());
        resultDTO.setStudentId(result.getStudent().getId());
        resultDTO.setMarksObtained(result.getMarksObtained());
        return resultDTO;
    }
}
