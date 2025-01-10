package com.example.proj.dto;

import lombok.Data;

@Data
public class LessonDTO {
    private Long id;

    private String title;

    private String content;

    private Integer duration;

    private Long courseId;
}
