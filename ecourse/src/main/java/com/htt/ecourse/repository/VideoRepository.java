package com.htt.ecourse.repository;

import com.htt.ecourse.pojo.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long> {
    List<Video> findByLessonId(Long lessonId);
}
