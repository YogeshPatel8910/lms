package com.example.proj.dto;

import com.example.proj.model.Result;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ExamDTO {

    private Long id;

    private String title;

    private Date date;

    private int duration;

    private Long courseId;

    private List<Result> result;
}
