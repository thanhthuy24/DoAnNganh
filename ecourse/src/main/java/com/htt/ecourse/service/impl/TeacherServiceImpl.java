package com.htt.ecourse.service.impl;

import com.htt.ecourse.dtos.TeacherDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.*;
import com.htt.ecourse.repository.CourseRepository;
import com.htt.ecourse.repository.TeacherRepository;
import com.htt.ecourse.repository.UserRepository;
import com.htt.ecourse.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Override
    public Teacher createTeacher(TeacherDTO teacherDTO) throws DataNotFoundException {
        User existUser = userRepository
                .findById(teacherDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("Can not find user by id: " + teacherDTO.getUserId()));

        Teacher newTeacher = Teacher.builder()
                .position(teacherDTO.getPosition())
                .description(teacherDTO.getDescription())
                .user(existUser)
                .build();

        return teacherRepository.save(newTeacher);
    }

    @Override
    public Teacher getTeacherById(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new DateTimeException("Can not find teacher by id: " + id));
    }

    @Override
    public TeacherDTO getTeacherDTOById(Long id) {
        return null;
    }

    @Override
    public Page<Teacher> getAllTeachers(Pageable pageable) {
        return teacherRepository.findAll(pageable);
    }

    @Override
    public Teacher updateTeacher(Long id, TeacherDTO teacherDTO) {
        Teacher existingTeacher = getTeacherById(id);
        if(existingTeacher != null) {
            User existingUser = userRepository.findById(teacherDTO.getUserId())
                    .orElseThrow(() -> new DateTimeException("Can not find user by id: " + teacherDTO.getUserId()));
            existingTeacher.setPosition(teacherDTO.getPosition());
            existingTeacher.setDescription(teacherDTO.getDescription());
            existingTeacher.setUser(existingUser);

            return teacherRepository.save(existingTeacher);
        }
        return null;
    }

    @Override
    public void deleteTeacher(Long id) {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(id);
        teacherRepository.delete(optionalTeacher.get());
    }
}
