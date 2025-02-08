package com.htt.ecourse.service.impl;

import com.htt.ecourse.dtos.*;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.*;
import com.htt.ecourse.repository.AssignmentRepository;
import com.htt.ecourse.repository.EnrollmentRepository;
import com.htt.ecourse.repository.QuestionRepository;
import com.htt.ecourse.repository.UserRepository;
import com.htt.ecourse.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.DateTimeException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final AssignmentRepository assignmentRepository;
    private final UserRepository userRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Override
    public List<QuestionChoiceDTO> getQuestionsByAssignmentId(Long assignmentId) {
        Assignment existingAssignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Can not find assignment!"));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = userRepository.getUserByUsername(username).getId();

        Optional<Enrollment> checkEnrollment = enrollmentRepository.findByUserIdAndCourseId(userId, existingAssignment.getCourse().getId());
        if (checkEnrollment.isEmpty()) {
            new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "This course isn't enrolled in your list! Please enroll before participating in this course!!");
        }

        List<Question> questions = questionRepository.findByAssignmentId(assignmentId);
        return questions.stream().map(this::convertToDTO).collect(Collectors.toList());
//        return questions;
    }

    @Override
    public List<QuestionEssayDTO> getQuestionEssaysByAssignmentId(Long assignmentId) {
        Assignment existingAssignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Can not find assignment!"));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = userRepository.getUserByUsername(username).getId();

        Optional<Enrollment> checkEnrollment = enrollmentRepository.findByUserIdAndCourseId(userId, existingAssignment.getCourse().getId());
        if (checkEnrollment.isEmpty()) {
            new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "This course isn't enrolled in your list! Please enroll before participating in this course!!");
        }

        List<Question> questions = questionRepository.findByAssignmentId(assignmentId);
        return questions.stream().map(this::convertEssayToDTO).collect(Collectors.toList());
    }

    private QuestionChoiceDTO convertToDTO(Question question) {
        List<Choice> choices = question.getChoices().stream()
                .map(choice -> Choice.builder()
                        .id(choice.getId())
                        .content(choice.getContent())
                        .isCorrect(choice.getIsCorrect())
                        .build())
                .collect(Collectors.toList());

        return QuestionChoiceDTO.builder()
                .id(question.getId())
                .content(question.getContent())
                .choices(choices)
                .build();
    }

    private QuestionEssayDTO convertEssayToDTO(Question question) {
        List<Essay> essays = question.getEssays().stream()
                .map(e -> Essay.builder()
                        .id(e.getId())
                        .content(e.getContent())
                        .build())
                .collect(Collectors.toList());

        return QuestionEssayDTO.builder()
                .id(question.getId())
                .content(question.getContent())
                .essays(essays)
                .build();
    }

    @Override
    public Question createQuestion(QuestionDTO questionDTO) throws DataNotFoundException {
        Assignment existingAssignment = assignmentRepository
                .findById(questionDTO.getAssignmentId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Can nit find course by id " + questionDTO.getAssignmentId()));

        if(existingAssignment != null) {
            Question question = Question.builder()
                    .content(questionDTO.getContent())
                    .assignment(existingAssignment)
                    .build();
            return questionRepository.save(question);
        }
        return null;
    }

    @Override
    public Question getQuestionById(Long id) {
        return questionRepository.findById(id).orElseThrow(() -> new DateTimeException("Can not find question!"));
    }

    @Override
    public Long countQuestionByAssignmentId(Long assignmentId) {
        Long countQues = questionRepository.countQuestionsByAssignmentId(assignmentId);
        return countQues;
    }

    @Override
    public Question updateQuestion(Long questionId, QuestionDTO questionDTO) {
        Question existinQuestion = getQuestionById(questionId);
        if(existinQuestion != null) {
            Assignment existingAssignment = assignmentRepository.findById(questionDTO.getAssignmentId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Can not find assignment!"));
           existinQuestion.setAssignment(existingAssignment);
           existinQuestion.setContent(questionDTO.getContent());
            return questionRepository.save(existinQuestion);
        }
        return null;
    }
}
