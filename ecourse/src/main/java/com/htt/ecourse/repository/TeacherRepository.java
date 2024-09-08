package com.htt.ecourse.repository;

import com.htt.ecourse.pojo.Teacher;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Page<Teacher> findAll(Pageable pageable); //phân trang
}
