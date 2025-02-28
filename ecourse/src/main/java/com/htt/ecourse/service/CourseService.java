package com.htt.ecourse.service;

import com.htt.ecourse.dtos.CourseDTO;
import com.htt.ecourse.dtos.VideoDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.exceptions.InvalidParamException;
import com.htt.ecourse.pojo.Course;
import com.htt.ecourse.pojo.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseService {
    List<Course> searchCourses(String keyword);
    Course createCourse(CourseDTO courseDTO) throws DataNotFoundException;
    Course getCourseById(Long id);
    Page<Course> getAllCourses(Pageable pageable);

    Page<Course> getCoursesByTeacherId(Long teacherId, Pageable pageable);

    Course updateCourse(Long id, CourseDTO courseDTO);
    void deleteCourse(Long id);
    boolean existByName(String name);
    List<Course> getAllCourseName();
    List<Course> getCoursesByCategoryId(Long categoryId);
    Page<Course> getCoursesByCategoryIdPage(Pageable pageable, Long categoryId);
    Page<Course> getCoursesByPrice(Float minPrice, Float maxPrice, Pageable pageable);

//    Page<Course> getCoursesByTeacher(Long teacherId, Pageable pageable);
}
