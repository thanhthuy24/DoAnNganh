package com.htt.ecourse.service;

import com.htt.ecourse.dtos.AnswerChoiceDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Answerchoice;
import com.htt.ecourse.responses.AnswerChoiceResponse;

import java.util.List;

public interface AnswerChoiceService {
    Answerchoice createAnswerChoice(AnswerChoiceDTO answerChoiceDTO) throws DataNotFoundException;
    List<AnswerChoiceResponse> checkAnswersByAssignmentId(Long assignmentId) throws DataNotFoundException;
    Answerchoice getAnswerChoice(Long questionId, Long assignmentId) throws DataNotFoundException;
}
