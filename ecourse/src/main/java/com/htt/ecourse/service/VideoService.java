package com.htt.ecourse.service;

import com.htt.ecourse.dtos.VideoDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Video;

import java.util.List;

public interface VideoService{
    Long countVideoByLessonId(Long lessonId);
    List<Video> findByLessonId(Long lessonId) throws DataNotFoundException;
    Video updateVideo(Long videoId, VideoDTO videoDTO);

    Long countVideoByCourseId(Long courseId);
}
