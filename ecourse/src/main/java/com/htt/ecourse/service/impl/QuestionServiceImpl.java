package com.htt.ecourse.service.impl;

import com.htt.ecourse.dtos.*;
import com.htt.ecourse.pojo.Assignment;
import com.htt.ecourse.pojo.Course;
import com.htt.ecourse.pojo.Lesson;
import com.htt.ecourse.pojo.Question;
import com.htt.ecourse.repository.AssignmentRepository;
import com.htt.ecourse.repository.QuestionRepository;
import com.htt.ecourse.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final AssignmentRepository assignmentRepository;

    @Override
    public List<QuestionChoiceDTO> getQuestionsByAssignmentId(Long assignmentId) {
        List<Question> questions = questionRepository.findByAssignmentId(assignmentId);
        return questions.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public Question createQuestion(QuestionDTO questionDTO) {
        Assignment existingAssignment = assignmentRepository
                .findById(questionDTO.getAssignmentId())
                .orElseThrow(() -> new DateTimeException("Can nit find course by id " + questionDTO.getAssignmentId()));

        if(existingAssignment != null) {
            Question question = Question.builder()
                    .content(questionDTO.getContent())
                    .assignment(existingAssignment)
                    .build();
            return questionRepository.save(question);
        }
        return null;
    }

    private QuestionChoiceDTO convertToDTO(Question question) {
        // Xây dựng danh sách VideoDTO bằng builder
        List<ChoiceDTO> choices = question.getChoices().stream()
                .map(choice -> ChoiceDTO.builder()
                        .content(choice.getContent())
                        .build())
                .collect(Collectors.toList());

        // Sử dụng builder để tạo LessonDTO
        return QuestionChoiceDTO.builder()
                .id(question.getId())
                .content(question.getContent())
                .choices(choices)
                .build();
    }
}
