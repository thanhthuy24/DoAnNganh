package com.htt.ecourse.service;

import com.htt.ecourse.dtos.CourseDTO;
import com.htt.ecourse.dtos.VideoDTO;
import com.htt.ecourse.exceptions.InvalidParamException;
import com.htt.ecourse.pojo.Course;
import com.htt.ecourse.pojo.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseService {
    public Course createCourse(CourseDTO courseDTO);
    Course getCourse(Long id);
    Page<Course> getAllCourses(Pageable pageable);
    Course updateCourse(Long id, CourseDTO courseDTO);
    void deleteCourse(Long id);
    boolean existByName(String name);
}
