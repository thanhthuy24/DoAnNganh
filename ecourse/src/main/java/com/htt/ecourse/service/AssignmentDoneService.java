package com.htt.ecourse.service;

import com.htt.ecourse.dtos.AssignmentDoneDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Userassignmentdone;

public interface AssignmentDoneService {
    Userassignmentdone createAssignmentDone(AssignmentDoneDTO assignmentDoneDTO);
    Userassignmentdone getAssignmentDone(Long assignmentId);
}
