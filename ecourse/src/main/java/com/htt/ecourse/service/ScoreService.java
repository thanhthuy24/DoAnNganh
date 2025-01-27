package com.htt.ecourse.service;

import com.htt.ecourse.dtos.ScoreDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Score;

public interface ScoreService {
    Score createScore(ScoreDTO scoreDTO) throws DataNotFoundException;
    Score getScoreByAssignmentId(Long assignmentId) throws DataNotFoundException;
    Score createScoreEssay(ScoreDTO scoreDTO, Long essayId) throws DataNotFoundException;
}
