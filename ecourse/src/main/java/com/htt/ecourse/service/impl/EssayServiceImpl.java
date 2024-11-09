package com.htt.ecourse.service.impl;

import com.htt.ecourse.dtos.EssayDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Assignment;
import com.htt.ecourse.pojo.Essay;
import com.htt.ecourse.pojo.Question;
import com.htt.ecourse.pojo.User;
import com.htt.ecourse.repository.AssignmentRepository;
import com.htt.ecourse.repository.EssayRepository;
import com.htt.ecourse.repository.QuestionRepository;
import com.htt.ecourse.repository.UserRepository;
import com.htt.ecourse.responses.AssignmentResponse;
import com.htt.ecourse.responses.EssayResponse;
import com.htt.ecourse.service.EssayService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EssayServiceImpl implements EssayService {
    private final EssayRepository essayRepository;
    private final AssignmentRepository assignmentRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    @Override
    public Essay createEssay(EssayDTO essayDTO) throws DataNotFoundException {
        Assignment existingAssignment = assignmentRepository.findById(essayDTO.getAssignmentId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Can not find assignment by id "+essayDTO.getAssignmentId() ));

        Question existingQuestion = questionRepository.findById(essayDTO.getQuestionId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Can not find question by id "+essayDTO.getQuestionId() ));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getUserByUsername(username);

        Essay newEssay = Essay.builder()
                .content(essayDTO.getContent())
                .assignment(existingAssignment)
                .question(existingQuestion)
                .user(user)
                .build();
        essayRepository.save(newEssay);
        return newEssay;
    }

    @Override
    public Essay updateEssay(Long essayId, EssayDTO essayDTO) throws DataNotFoundException {
        Essay existingEssay = essayRepository.findById(essayId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Can not find essay by id " + essayId));

        Assignment existingAssignment = assignmentRepository.findById(essayDTO.getAssignmentId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Can not find assignment by id "+essayDTO.getAssignmentId() ));

        Question existingQuestion = questionRepository.findById(essayDTO.getQuestionId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Can not find question by id "+essayDTO.getQuestionId() ));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getUserByUsername(username);

        if(existingEssay != null){
            existingEssay.setContent(essayDTO.getContent());
            existingEssay.setAssignment(existingAssignment);
            existingEssay.setQuestion(existingQuestion);
            existingEssay.setUser(user);
            essayRepository.save(existingEssay);
        }
        return null;
    }

    @Override
    public Page<EssayResponse> getEssaysByAssignment(Long assignmentId, PageRequest pageRequest) {
        return essayRepository.findByAssignmentId(assignmentId, pageRequest)
                .map(EssayResponse::fromEssay);
    }
}
