package com.htt.ecourse.service.impl;

import com.htt.ecourse.dtos.CourseDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Category;
import com.htt.ecourse.pojo.Course;
import com.htt.ecourse.pojo.Tag;
import com.htt.ecourse.pojo.Teacher;
import com.htt.ecourse.repository.CategoryRepository;
import com.htt.ecourse.repository.CourseRepository;
import com.htt.ecourse.repository.TagRepository;
import com.htt.ecourse.repository.TeacherRepository;
import com.htt.ecourse.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;
    private final TeacherRepository teacherRepository;
    private final TagRepository tagRepository;

    @Override
    public Course createCourse(CourseDTO courseDTO) throws DataNotFoundException {
        Category existCategory = categoryRepository
                .findById(courseDTO.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException("Can not find category by id: " + courseDTO.getCategoryId()));

        Tag existTag = tagRepository
                .findById(courseDTO.getTagId())
                .orElseThrow(() -> new DataNotFoundException("Can not find tag by id: " + courseDTO.getTagId()));

        Teacher existTeacher = teacherRepository
                .findById(courseDTO.getTeacherId())
                .orElseThrow(() -> new DateTimeException("Can not find teacher by id: " + courseDTO.getTeacherId()));

        Course newCourse = Course.builder()
                .name(courseDTO.getName())
                .description(courseDTO.getDescription())
                .image(courseDTO.getImage())
                .price(courseDTO.getPrice())
                .discount(courseDTO.getDiscount())
                .category(existCategory)
                .tag(existTag)
                .teacher(existTeacher)
                .build();
        courseRepository.save(newCourse);
        return newCourse;
    }

    @Override
    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new DateTimeException("Can not find course with id : " + id));
    }

    @Override
    public Page<Course> getAllCourses(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }

    @Override
    public List<Course> getCoursesByTeacherId(Long teacherId) {
        return courseRepository.findByTeacherId(teacherId);
    }

    @Override
    public Course updateCourse(Long id, CourseDTO courseDTO) {
        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(() -> new DateTimeException("Can not find course with id : " + id));
        if(existingCourse != null){
            //copy thuộc tính từ DTO sang
            Category existCategory = categoryRepository.findById(courseDTO.getCategoryId())
                    .orElseThrow(() -> new DateTimeException("Can not find category by id: " + courseDTO.getCategoryId()));

            Teacher existTeacher = teacherRepository.findById(courseDTO.getTeacherId())
                            .orElseThrow(() -> new DateTimeException("Can not find teacher by id: " + courseDTO.getTeacherId()));
            existingCourse.setName(courseDTO.getName());
            existingCourse.setDescription(courseDTO.getDescription());
            existingCourse.setPrice(courseDTO.getPrice());
            existingCourse.setDiscount(courseDTO.getDiscount());
            existingCourse.setCategory(existCategory);
            existingCourse.setTeacher(existTeacher);
            existingCourse.setUpdatedDate(new Date());

            return courseRepository.save(existingCourse);
            //sử dụng ModelMapper
        }
        return null;
    }

    @Override
    public void deleteCourse(Long id) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        optionalCourse.ifPresent(courseRepository::delete);
    }

    @Override
    public boolean existByName(String name) {
        return courseRepository.existsByName(name);
    }


}
