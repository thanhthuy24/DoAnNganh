package com.htt.ecourse.service;

import com.htt.ecourse.dtos.AssignmentDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Assignment;
import com.htt.ecourse.responses.AssignmentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AssignmentService {
    Page<AssignmentResponse> getAllAssignment(PageRequest pageRequest);
    Assignment getAssignmentById(Long id);
    Assignment createAssignment(AssignmentDTO assignmentDTO) throws DataNotFoundException;

    List<Assignment> getAssignmentByLessonId(Long lessonId);
    List<Assignment> getAssignmentByCourseId(Long courseId);

    Assignment updateAssignment(Long assignmentId, AssignmentDTO assignmentDTO) throws DataNotFoundException;
    void deleteAssignment(Long id);

}
