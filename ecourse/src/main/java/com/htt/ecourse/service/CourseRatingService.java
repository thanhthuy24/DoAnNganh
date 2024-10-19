package com.htt.ecourse.service;

import com.htt.ecourse.dtos.CourseRatingDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Courserating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface CourseRatingService {
    Courserating createRating(CourseRatingDTO courseRatingDTO) throws DataNotFoundException;
    Page<Courserating> getRatingByCourseId(Long courseId, PageRequest pageRequest) throws DataNotFoundException;
    Float averageRatingByCourseId(Long courseId) throws DataNotFoundException;

    Long countRatingByCourseIdByRating(Long courseId, Long rating) throws DataNotFoundException;
    Float averageRatingByStar(Long rate, Long courseId) throws DataNotFoundException;
}
