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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Can not find category by id: " + courseDTO.getCategoryId()));

        Tag existTag = tagRepository
                .findById(courseDTO.getTagId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Can not find tag by id: " + courseDTO.getTagId()));

        Teacher existTeacher = teacherRepository
                .findById(courseDTO.getTeacherId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Can not find teacher by id: " + courseDTO.getTeacherId()));

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
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Can not find course with id : " + id));
    }

    public List<Course> searchCourses(String keyword) {
        return courseRepository.searchByKeyword(keyword);
    }

    @Override
    public Page<Course> getAllCourses(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }

    @Override
    public List<Course> getAllCourseName(){
        return courseRepository.findAll();
    }

    @Override
    public List<Course> getCoursesByCategoryId(Long categoryId) {
//        Category existingCate = categoryRepository.findById(categoryId)
//                .orElseThrow(() -> new ResponseStatusException(
//                        HttpStatus.NOT_FOUND, "Can not find category by id: " + categoryId));
        if (categoryId != null) {
            return courseRepository.findByCategoryId(categoryId);
        }
        else {
            return courseRepository.findAll();
        }
    }

    @Override
    public Page<Course> getCoursesByCategoryIdPage(Pageable pageable, Long categoryId) {
        if (categoryId != null) {
            return courseRepository.findByCategoryId(categoryId, pageable);
        }
        else {
            return courseRepository.findAll(pageable);
        }
    }

    @Override
    public Page<Course> getCoursesByPrice(Float minPrice, Float maxPrice, Pageable pageable) {
        if (minPrice != null && maxPrice != null) {
            return courseRepository.getCoursesByPrice(minPrice, maxPrice, pageable);
        }
        else {
            return courseRepository.findAll(pageable);
        }
    }

    @Override
    public List<Course> getCoursesByTeacherId(Long teacherId) {
        return courseRepository.findByTeacherId(teacherId);
    }

    @Override
    public Course updateCourse(Long id, CourseDTO courseDTO) {
        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Can not find course with id : " + id));
        if(existingCourse != null){
            //copy thuộc tính từ DTO sang
            Category existCategory = categoryRepository.findById(courseDTO.getCategoryId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Can not find category by id: " + courseDTO.getCategoryId()));

            Teacher existTeacher = teacherRepository.findById(courseDTO.getTeacherId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Can not find teacher by id: " + courseDTO.getTeacherId()));
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

    @Override
    public List<Course> getCoursesByTeacher(Long teacherId) throws DataNotFoundException {
        Teacher existingTeacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Cannot find teacher with id " + teacherId));

        List<Course> list = courseRepository.findCourseByTeacherId(teacherId);
        if (!list.isEmpty()){
            return list;
        }
        return null;
    }


}
