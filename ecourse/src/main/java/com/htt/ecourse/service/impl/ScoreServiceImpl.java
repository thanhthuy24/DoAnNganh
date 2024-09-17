package com.htt.ecourse.service.impl;

import com.htt.ecourse.dtos.ScoreDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Assignment;
import com.htt.ecourse.pojo.Score;
import com.htt.ecourse.pojo.User;
import com.htt.ecourse.repository.AssignmentRepository;
import com.htt.ecourse.repository.ScoreRepository;
import com.htt.ecourse.repository.UserRepository;
import com.htt.ecourse.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScoreServiceImpl implements ScoreService {
    private final ScoreRepository scoreRepository;
    private final UserRepository userRepository;
    private final AssignmentRepository assignmentRepository;

    @Override
    public Score createScore(ScoreDTO scoreDTO) throws DataNotFoundException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getUserByUsername(username);

        Assignment existingAssignment = assignmentRepository
                .findById(scoreDTO.getAssignmentId()).orElseThrow(() -> new DataNotFoundException("Assignment not found!!"));

        Optional<Score> existingScore = scoreRepository
                .findByUserIdAndAssignmentId(user.getId(), existingAssignment.getId());
        if (existingScore.isPresent()) {
            throw new DataNotFoundException("Score already exist!!!");
        }

        Score newScore = Score.builder()
                .score(scoreDTO.getScore())
                .feedBack(scoreDTO.getFeedback())
                .assignment(existingAssignment)
                .user(user)
                .build();

        scoreRepository.save(newScore);

        return newScore;
    }
}
