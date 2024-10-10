package com.htt.ecourse.repository;

import com.htt.ecourse.pojo.Teacher;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Page<Teacher> findAll(Pageable pageable); //phân trang

    @Query("SELECT t FROM Teacher t WHERE t.user.id = :userId")
    List<Teacher> findByUserId(Long userId);
}
