package com.htt.ecourse.service.impl;

import com.htt.ecourse.dtos.CourseDTO;
import com.htt.ecourse.pojo.Category;
import com.htt.ecourse.pojo.Course;
import com.htt.ecourse.repository.CategoryRepository;
import com.htt.ecourse.repository.CourseRepository;
import com.htt.ecourse.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Course createCourse(CourseDTO courseDTO) {
        Category existCategory = categoryRepository.findById(courseDTO.getCategoryId())
                .orElseThrow(() -> new DateTimeException("Can not find category by id: " + courseDTO.getCategoryId()));

        Course newCourse = Course.builder()
                .name(courseDTO.getName())
                .description(courseDTO.getDescription())
                .image(courseDTO.getImage())
                .price(courseDTO.getPrice())
                .discount(courseDTO.getDiscount())
                .category(existCategory)
                .build();

        return courseRepository.save(newCourse);
    }

    @Override
    public Course getCourse(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new DateTimeException("Can not find course with id : " + id));
    }

    @Override
    public Page<Course> getAllCourses(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }

    @Override
    public Course updateCourse(Long id, CourseDTO courseDTO) {
        Course existingCourse = getCourse(id);
        if(existingCourse != null){
            //copy thuộc tính từ DTO sang
            Category existCategory = categoryRepository.findById(courseDTO.getCategoryId())
                    .orElseThrow(() -> new DateTimeException("Can not find category by id: " + courseDTO.getCategoryId()));
            existingCourse.setName(courseDTO.getName());
            existingCourse.setDescription(courseDTO.getDescription());
            existingCourse.setImage(courseDTO.getImage());
            existingCourse.setPrice(courseDTO.getPrice());
            existingCourse.setDiscount(courseDTO.getDiscount());
            existingCourse.setCategory(existCategory);

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
