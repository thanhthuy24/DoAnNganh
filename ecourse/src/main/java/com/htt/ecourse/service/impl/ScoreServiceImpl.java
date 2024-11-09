package com.htt.ecourse.service.impl;

import com.htt.ecourse.dtos.ScoreDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Answerchoice;
import com.htt.ecourse.pojo.Assignment;
import com.htt.ecourse.pojo.Score;
import com.htt.ecourse.pojo.User;
import com.htt.ecourse.repository.*;
import com.htt.ecourse.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScoreServiceImpl implements ScoreService {
    private final ScoreRepository scoreRepository;
    private final UserRepository userRepository;
    private final AssignmentRepository assignmentRepository;
    private final AnswerChoiceRepository answerChoiceRepository;
    private final QuestionRepository questionRepository;

    @Override
    public Score getScoreByAssignmentId(Long assignmentId) throws DataNotFoundException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getUserByUsername(username);

        Assignment existingAssignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Assignment not found!!"));

        Score score = scoreRepository.findByAssignmentId(assignmentId);
        if (score == null) {
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Score not found!!!");
        }
        return score;
    }

    @Override
    public Score createScore(ScoreDTO scoreDTO) throws DataNotFoundException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getUserByUsername(username);

        Assignment existingAssignment = assignmentRepository
                .findById(scoreDTO.getAssignmentId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Assignment not found!!"));

        Optional<Score> existingScore = scoreRepository
                .findByUserIdAndAssignmentId(user.getId(), existingAssignment.getId());
        if (existingScore.isPresent()) {
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Score already exist!!!");
        }

        List<Answerchoice> answerchoiceList = answerChoiceRepository
                .findByAssignmentIdAndUserId(existingAssignment.getId(), user.getId());

        if (answerchoiceList.isEmpty()) {
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Answer choice not found!!!");
        }

        Long countQues = questionRepository.countQuestionsByAssignmentId(existingAssignment.getId());

        float score = 0L;
        for (Answerchoice answer : answerchoiceList){
            if (answer.getChoice().getIsCorrect())
                score++;
        }

        double percentage = (double) score / countQues;
        String feedback = "";
        if (percentage >= 80){
            feedback = "Bravo!!!";
        } else if (percentage >= 60){
            feedback = "Good!";
        } else if (percentage < 60){
            feedback = "You need to be more careful!";
        }

        Score newScore = Score.builder()
                .score(score)
                .feedBack(feedback)
                .assignment(existingAssignment)
                .user(user)
                .build();

        scoreRepository.save(newScore);
        return newScore;
    }
}
