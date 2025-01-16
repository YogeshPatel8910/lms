package com.example.proj.service;

import com.example.proj.dto.LessonDTO;
import com.example.proj.model.Course;
import com.example.proj.model.Lesson;
import com.example.proj.repositry.LessonRepositry;
import com.example.proj.utils.GenericObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class LessonService {

    @Autowired
    private LessonRepositry lessonRepositry;

    @Autowired
    private CourseService courseService;

    public Page<LessonDTO> getAllLesson(int page, int size, String sortBy, String direction) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(sortDirection, sortBy);  // Multiple fields can be added here
        Pageable pageable = PageRequest.of(page, size, sort);
        return lessonRepositry.findAll(pageable).map(this::mapToDTO);
    }

    public Page<LessonDTO> getAllLessonByCourse(long id,int page, int size, String sortBy, String direction) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(sortDirection, sortBy);  // Multiple fields can be added here
        Pageable pageable = PageRequest.of(page, size, sort);
        return lessonRepositry.findAllByCourseId(id,pageable).map(this::mapToDTO);
    }

    public LessonDTO createLesson(long id, LessonDTO lessonDTO) {
        Course course = courseService.getCourse(id);
        Lesson lesson = GenericObjectMapper.map(lessonDTO,Lesson.class);
        lesson.setCourse(course);
        Lesson saved = lessonRepositry.save(lesson);
        return mapToDTO(saved);
    }

    public LessonDTO updateLesson(long id, LessonDTO lessonDTO) {
        Lesson lesson = lessonRepositry.findById(id).orElse(null);
        if(lesson != null){
            lesson.setTitle(lessonDTO.getTitle());
            lesson.setContent(lessonDTO.getContent());
            lesson.setDuration(lessonDTO.getDuration());
            return mapToDTO(lesson);
        }
        else
            return null;
    }

    public boolean deleteLesson(long id) {
        boolean isPresent = lessonRepositry.existsById(id);
        if(isPresent){
            lessonRepositry.deleteById(id);
            return true;
        }
        else
            return false;
    }

    public LessonDTO getLessonById(long id) {
        return lessonRepositry.findById(id).map(this::mapToDTO).orElse(null);
    }

    private LessonDTO mapToDTO(Lesson lesson) {
        LessonDTO lessonDTO = GenericObjectMapper.map(lesson,LessonDTO.class);
        lessonDTO.setCourseId(lesson.getCourse().getId());
        return lessonDTO;
    }

}
