package com.htt.ecourse.service.impl;

import com.htt.ecourse.dtos.ChoiceDTO;
import com.htt.ecourse.pojo.Choice;
import com.htt.ecourse.pojo.Question;
import com.htt.ecourse.repository.ChoiceRepository;
import com.htt.ecourse.repository.QuestionRepository;
import com.htt.ecourse.service.ChoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChoiceServiceImpl implements ChoiceService {
    private final ChoiceRepository choiceRepository;
    private final QuestionRepository questionRepository;

    @Override
    public Choice createChoice(ChoiceDTO choiceDTO) {
//        Question existingQuestion = questionRepository.findById(choiceDTO.getQuestionId());
        Question existingQuestion = questionRepository.findById(choiceDTO.getQuestionId()).get();
        if (existingQuestion != null) {
            Choice choice = Choice.builder()
                    .content(choiceDTO.getContent())
                    .question(existingQuestion)
                    .isCorrect(choiceDTO.isCorrect())
                    .build();
            return choiceRepository.save(choice);
        }
        return null;
    }

    @Override
    public Choice updateChoice(Long choiceId, ChoiceDTO choiceDTO) {
        Choice existingChoice = choiceRepository.findById(choiceId).get();
        if (existingChoice != null) {
            Question existingQuestion = questionRepository.findById(choiceDTO.getQuestionId()).get();
            existingChoice.setContent(choiceDTO.getContent());
            existingChoice.setQuestion(existingQuestion);
            existingChoice.setIsCorrect(choiceDTO.isCorrect());
            return choiceRepository.save(existingChoice);
        }
        return null;
    }
}
