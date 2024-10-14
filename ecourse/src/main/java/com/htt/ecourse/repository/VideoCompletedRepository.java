package com.htt.ecourse.repository;

import com.htt.ecourse.pojo.Lesson;
import com.htt.ecourse.pojo.Video;
import com.htt.ecourse.pojo.Videocompleted;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VideoCompletedRepository extends JpaRepository<Videocompleted, Long> {
    Videocompleted findByVideoIdAndUserId(Long videoId, Long userId);
    Long countByUserIdAndVideoId(Long userId, Long videoId);

    List<Videocompleted> findByUserId(Long userId);

    @Query("SELECT COUNT(h.id) FROM Videocompleted h WHERE h.user.id = :userId AND h.video.lesson.id = :lessonId")
    Long countWatchedVideosByUserAndLessonId(@Param("userId") Long userId, @Param("lessonId") Long lessonId);

}
