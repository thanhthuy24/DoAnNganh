package com.htt.ecourse.repository;

import com.htt.ecourse.pojo.Lesson;
import com.htt.ecourse.pojo.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VideoRepository extends JpaRepository<Video, Long> {
    List<Video> findByLessonId(Long lessonId);
    Optional<Video> findById(Long id);
//    Long countByLessonId(List<Lesson> lessons);
    Long countByLessonId(Long lessonId);
}
