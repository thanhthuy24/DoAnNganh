package com.htt.ecourse.service;

import com.htt.ecourse.dtos.TeacherDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TeacherService {
    Teacher createTeacher(TeacherDTO teacherDTO) throws DataNotFoundException;
    Teacher getTeacherById(Long id);
    TeacherDTO getTeacherDTOById(Long id);
    Page<Teacher> getAllTeachers(Pageable pageable);
    Teacher updateTeacher(Long id, TeacherDTO teacherDTO);
    void deleteTeacher(Long id);
    List<Teacher> getTeacherByUserId(Long userId);
}
