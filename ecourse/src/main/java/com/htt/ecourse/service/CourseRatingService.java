package com.htt.ecourse.service;

import com.htt.ecourse.dtos.CourseRatingDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Courserating;

public interface CourseRatingService {
    Courserating createRating(CourseRatingDTO courseRatingDTO) throws DataNotFoundException;
}
