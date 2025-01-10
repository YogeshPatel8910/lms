package com.example.proj.service;

import com.example.proj.dto.LessonDTO;
import com.example.proj.model.Course;
import com.example.proj.model.Lesson;
import com.example.proj.repositry.LessonRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Lesson lesson = new Lesson();
        lesson.setTitle(lessonDTO.getTitle());
        lesson.setContent(lessonDTO.getContent());
        lesson.setDuration(lessonDTO.getDuration());
        lesson.setCourse(course);
        Lesson saved = lessonRepositry.save(lesson);
        return mapToDTO(saved);
    }

    public Optional<LessonDTO> updateLesson(long id, LessonDTO lessonDTO) {
        Optional<Lesson> lesson = lessonRepositry.findById(id);
        if(lesson.isPresent()){
            Lesson recieved = lesson.get();
            recieved.setTitle(lessonDTO.getTitle());
            recieved.setContent(lessonDTO.getContent());
            recieved.setDuration(lessonDTO.getDuration());
            return Optional.of(mapToDTO(recieved));
        }
        else
            return Optional.empty();
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


    private List<LessonDTO> map(List<Lesson> lessons) {
        return lessons.stream().map(this::mapToDTO).toList();
    }
    private LessonDTO mapToDTO(Lesson lesson) {
        LessonDTO lessonDTO = new LessonDTO();
        lessonDTO.setId(lesson.getId());
        lessonDTO.setTitle(lesson.getTitle());
        lessonDTO.setContent(lesson.getContent());
        lessonDTO.setDuration(lesson.getDuration());
        lessonDTO.setCourseId(lesson.getCourse().getId());
        return lessonDTO;
    }
}
