package com.htt.ecourse.service;

import com.htt.ecourse.dtos.CourseRatingDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Courserating;
import org.json.JSONException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface CourseRatingService {
    Courserating createRating(CourseRatingDTO courseRatingDTO) throws DataNotFoundException, JSONException;
    Page<Courserating> getRatingByCourseId(Long courseId, PageRequest pageRequest) throws DataNotFoundException;
    Float averageRatingByCourseId(Long courseId) throws DataNotFoundException;

    Long countAll(Long courseId) throws DataNotFoundException;
    Long countRatingByCourseIdByRating(Long courseId, Long rating) throws DataNotFoundException;
    Float averageRatingByStar(Long rate, Long courseId) throws DataNotFoundException;

    Page<Courserating> getRatingsBySentiment(Long courseId, String sentiment, PageRequest pageRequest);
    Page<Courserating> getRatingsByRate(Long courseId, Long rate, PageRequest pageRequest);
}
