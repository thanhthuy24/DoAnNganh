package com.htt.ecourse.service;

import com.htt.ecourse.dtos.EssayDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Essay;
import com.htt.ecourse.responses.EssayResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EssayService {
    Essay createEssay(EssayDTO essayDTO) throws DataNotFoundException;
    Essay updateEssay(Long essayId, EssayDTO essayDTO) throws DataNotFoundException;
//    List<Essay> getEssaysByAssignment(Long assignmentId);
    Page<EssayResponse> getEssaysByAssignment(Long assignmentId, PageRequest pageRequest);
}
