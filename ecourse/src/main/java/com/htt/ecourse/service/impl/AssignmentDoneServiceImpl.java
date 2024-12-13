package com.htt.ecourse.service.impl;

import com.htt.ecourse.dtos.AssignmentDoneDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Assignment;
import com.htt.ecourse.pojo.Enrollment;
import com.htt.ecourse.pojo.User;
import com.htt.ecourse.pojo.Userassignmentdone;
import com.htt.ecourse.repository.AssignmentDoneRepository;
import com.htt.ecourse.repository.AssignmentRepository;
import com.htt.ecourse.repository.EnrollmentRepository;
import com.htt.ecourse.repository.UserRepository;
import com.htt.ecourse.service.AssignmentDoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.DateTimeException;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssignmentDoneServiceImpl implements AssignmentDoneService {
    private final AssignmentDoneRepository assignmentDoneRepository;
    private final UserRepository userRepository;
    private final AssignmentRepository assignmentRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Override
    public Userassignmentdone createAssignmentDone(AssignmentDoneDTO assignmentDoneDTO){
        Assignment existingAssignment = assignmentRepository.findById(assignmentDoneDTO.getAssignmentId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Can not find Assignment with id "
                        + assignmentDoneDTO.getAssignmentId()));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getUserByUsername(username);

        Optional<Enrollment> checkEnrollment = enrollmentRepository
                .findByUserIdAndCourseId(user.getId(), existingAssignment.getCourse().getId());
        if (checkEnrollment.isEmpty()) {
            new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "This course isn't enrolled in your list! Please enroll before participating in this course!!");
        }

        Userassignmentdone newAssignmentDone = Userassignmentdone.builder()
                .assignment(existingAssignment)
                .user(user)
                .createdDate(new Date())
                .build();

        assignmentDoneRepository.save(newAssignmentDone);
        return newAssignmentDone;
    }

    @Override
    public Userassignmentdone getAssignmentDone(Long assignmentId) {
        Assignment existingAssignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Can not find Assignment with id "
                        + assignmentId));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getUserByUsername(username);

        Userassignmentdone assignmentDone = assignmentDoneRepository.findByUserIdAndAssignmentId(user.getId(), assignmentId);
        if (assignmentDone == null) {
            return null;
        }
        return assignmentDone;
    }

    @Override
    public Long getCountByAssignmentId (Long assignmentId) {
        Assignment existingAssignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Can not find Assignment with id "
                        + assignmentId));
        Long count = assignmentDoneRepository.countByAssignmentId(existingAssignment.getId());
        if (count == 0) {
            return 0L;
        } return count;
    }

}
