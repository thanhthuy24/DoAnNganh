package com.htt.ecourse.service;

import com.htt.ecourse.dtos.CourseDTO;
import com.htt.ecourse.pojo.Course;

import java.util.List;

public interface RecommendationService {
    List<CourseDTO> getRecommendedCourses(Long userId);
    List<Course> getRecommendedCourses2(Long userId);
}
