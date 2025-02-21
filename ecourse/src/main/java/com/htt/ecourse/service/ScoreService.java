package com.htt.ecourse.service;

import com.htt.ecourse.dtos.ScoreDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Score;

import java.util.List;
import java.util.Optional;

public interface ScoreService {
    Score createScore(ScoreDTO scoreDTO) throws DataNotFoundException;
    List<Score> getScoreByAssignmentId(Long assignmentId) throws DataNotFoundException;
    Score createScoreEssay(ScoreDTO scoreDTO, Long essayId) throws DataNotFoundException;
    Optional<Score> getScoreByAssignmentIdAndUserId(Long assignmentId, Long userId);
}
