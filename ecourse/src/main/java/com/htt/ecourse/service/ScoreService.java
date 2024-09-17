package com.htt.ecourse.service;

import com.htt.ecourse.dtos.ScoreDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Score;

public interface ScoreService {
    Score createScore(ScoreDTO scoreDTO) throws DataNotFoundException;
}
