package com.htt.ecourse.service.impl;

import com.htt.ecourse.dtos.AnswerChoiceDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.*;
import com.htt.ecourse.repository.*;
import com.htt.ecourse.responses.AnswerChoiceResponse;
import com.htt.ecourse.service.AnswerChoiceService;
import com.htt.ecourse.service.AssignmentDoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnswerChoiceServiceImpl implements AnswerChoiceService {
    private final AnswerChoiceRepository answerChoiceRepository;
    private final QuestionRepository questionRepository;
    private final AssignmentRepository assignmentRepository;
    private final UserRepository userRepository;
    private final ChoiceRepository choiceRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Override
    public Answerchoice createAnswerChoice(AnswerChoiceDTO answerChoiceDTO, Long assignmentId) throws DataNotFoundException {
        Assignment existingAssignment = assignmentRepository
                .findById(assignmentId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Can not find Assignment with id " + assignmentId));

        LocalDateTime dueDate = Instant.ofEpochMilli(existingAssignment.getDueDate().getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        // Kiểm tra nếu dueDate đã qua hạn
        if (dueDate.isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The due date for this assignment has passed. You cannot submit an answer choice.");
        }

        Question existingQuestion = questionRepository
                .findById(answerChoiceDTO.getQuestionId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Can not find question with id " + answerChoiceDTO.getQuestionId()));

        Choice existingChoice = choiceRepository
                .findById(answerChoiceDTO.getChoiceId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Can not find question with id " + answerChoiceDTO.getChoiceId()));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getUserByUsername(username);

        Optional<Enrollment> checkEnrollment = enrollmentRepository.findByUserIdAndCourseId(user.getId(), existingAssignment.getCourse().getId());
        if (checkEnrollment.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "This course isn't enrolled in your list! Please enroll before participating in this course!!");
        }

        Optional<Answerchoice> checkAnswerchoice = answerChoiceRepository
                .findByAssignmentIdAndQuestionIdAndUserId
                        (
                            assignmentId,
                            existingQuestion.getId(),
                            user.getId());
        if (checkAnswerchoice.isPresent()) {
            throw new DataNotFoundException("This answer choice already exists!");
        }

        Answerchoice newAnswer = Answerchoice.builder()
                .choice(existingChoice)
                .assignment(existingAssignment)
                .question(existingQuestion)
                .user(user)
                .build();
        answerChoiceRepository.save(newAnswer);
        return newAnswer;
    }

    @Override
    public List<AnswerChoiceResponse> checkAnswersByAssignmentId(Long assignmentId) {
        Assignment existingAssignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Can not find Assignment with id " + assignmentId));

        List<Question> existingQuestions = questionRepository.findByAssignmentId(assignmentId);
        List<Choice> existingChoice = choiceRepository.findByQuestionIn(existingQuestions);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = userRepository.getUserByUsername(username).getId();

        Optional<Enrollment> checkEnrollment = enrollmentRepository.findByUserIdAndCourseId(userId, existingAssignment.getCourse().getId());
        if (checkEnrollment.isEmpty()) {
            new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "This course isn't enrolled in your list! Please enroll before participating in this course!!");
        }

        List<Answerchoice> list = answerChoiceRepository.findByAssignmentIdAndUserId(assignmentId, userId);
        return list.stream()
                .map(this::convertToResponse)  // Sử dụng phương thức chuyển đổi
                .collect(Collectors.toList());
    }

    private AnswerChoiceResponse convertToResponse(Answerchoice answerChoice) {
        return AnswerChoiceResponse.builder()
                .id(answerChoice.getId())
                .assignmentId(answerChoice.getAssignment().getId())
                .questionId(answerChoice.getQuestion().getId())
                .userId(answerChoice.getUser().getId())
                .choiceId(answerChoice.getChoice().getId())
                .build();
    }

//    @Override
//    public Answerchoice getAnswerChoice(Long questionId, Long assignmentId) throws DataNotFoundException {
//        return null;
//        Assignment existingAssignment = assignmentRepository.findById(assignmentId)
//                .orElseThrow(() -> new DataNotFoundException("Assignment not found"));
//
//        Question existingQuestion = questionRepository.findById(questionId)
//                .orElseThrow(() -> new DataNotFoundException("Question not found"));
//
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        Long userId = userRepository.getUserByUsername(username).getId();
//
//        Optional<Enrollment> checkEnrollment = enrollmentRepository.findByUserIdAndCourseId(userId, existingAssignment.getCourse().getId());
//        if (checkEnrollment.isEmpty()) {
//            throw new DateTimeException("This course isn't enrolled in your list! Please enroll before participating in this course!!");
//        }
//
//        Optional<Answerchoice> check = answerChoiceRepository.findByAssignmentIdAndQuestionId(assignmentId, questionId);
//        return check.orElse(null);

//    }

}
