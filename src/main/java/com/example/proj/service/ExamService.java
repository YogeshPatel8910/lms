package com.example.proj.service;

import com.example.proj.dto.ExamDTO;
import com.example.proj.model.Course;
import com.example.proj.model.Exam;
import com.example.proj.repositry.ExamRepositry;
import com.example.proj.utils.GenericObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ExamService {

    @Autowired
    private ExamRepositry examRepositry;

    @Autowired
    private CourseService courseService;

    public Page<ExamDTO> getAllExam(int page, int size, String sortBy, String direction) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(sortDirection, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return examRepositry.findAll(pageable).map(this::mapToDTO);
    }

    public Page<ExamDTO> getAllExamByCourse(long id,int page, int size, String sortBy, String direction) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(sortDirection, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return examRepositry.findAllByCourseId(id,pageable).map(this::mapToDTO);
    }

    public ExamDTO createExam(long id, ExamDTO examDTO) {
        Course course = courseService.getCourse(id);
        Exam exam = GenericObjectMapper.map(examDTO,Exam.class);
        exam.setCourse(course);
        Exam savedExam = examRepositry.save(exam);
        return mapToDTO(savedExam);
    }

    public ExamDTO updateExam(long id, ExamDTO examDTO) {
        Exam exam = examRepositry.findById(id).orElse(null);
        if(exam!=null){
            exam.setDate(examDTO.getDate());
            return mapToDTO(exam);
        }
        else
            return null;
    }

    public boolean deleteExam(long id) {
        boolean isPresent = examRepositry.existsById(id);
        if(isPresent){
            examRepositry.deleteById(id);
            return true;
        }
        else
            return false;
    }

    public ExamDTO getExamById(long id) {
        return examRepositry.findById(id).map(this::mapToDTO).orElse(null);
    }

    private ExamDTO mapToDTO(Exam exam) {
        ExamDTO examDTO = GenericObjectMapper.map(exam,ExamDTO.class);
        examDTO.setCourseId(exam.getCourse().getId());
        examDTO.setResult(exam.getResult());
        return examDTO;
    }

}
