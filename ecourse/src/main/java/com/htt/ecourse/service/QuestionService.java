package com.htt.ecourse.service;

import com.htt.ecourse.dtos.QuestionChoiceDTO;
import com.htt.ecourse.dtos.QuestionDTO;
import com.htt.ecourse.pojo.Question;

import java.util.List;

public interface QuestionService {
    List<QuestionChoiceDTO> getQuestionsByAssignmentId(Long assignmentId);
    Question createQuestion(QuestionDTO questionDTO);
}
